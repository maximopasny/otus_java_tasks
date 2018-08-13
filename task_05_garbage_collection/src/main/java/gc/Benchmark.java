package gc;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Benchmark implements BenchmarkMBean {
    private volatile int size = 100;
    private static List<GcInfo> youngGcInfos = new ArrayList<>();
    private static List<GcInfo> oldGcInfos = new ArrayList<>();

    private static Boolean youngCounted = false;
    private static Boolean oldCounted = false;

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        installGCMonitoring();

        System.out.println("Thread Running");
        List<String> holder = new ArrayList<>();
        long startTime = System.nanoTime();

        try {
            while (true) {
//                System.out.println("put strings");
                List<String> appendix = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    appendix.add(new String("123"));
                }
                holder.addAll(appendix);

//                System.out.println("remove strings");
                holder = holder.subList(0, size / 2);

//                System.out.println("iteration done! " + new Date());
                Benchmark.youngCounted = false;
                Benchmark.oldCounted = false;
                Thread.sleep(1000);
            }
        } catch (OutOfMemoryError outOfMemoryError) {
            System.out.println("<------------------------------------------>");

            var youngGcInfosCopy = new ArrayList<>(youngGcInfos);
            var youngCount = youngGcInfos.size();
            System.out.println("Youngs count: " + youngCount);
            var youngTimeTotal = youngGcInfosCopy.stream().mapToLong(GcInfo::getDuration).sum();
            System.out.println("Total young gc time consumed " + youngTimeTotal);
            System.out.println("Avg young gc time consumed " + youngTimeTotal/youngGcInfos.size());


            var oldGcInfosCopy = new ArrayList<>(oldGcInfos);
            var oldCount = oldGcInfos.size();
            System.out.println("Olds count: " + oldCount);
            var oldTimeTotal = oldGcInfosCopy.stream().mapToLong(GcInfo::getDuration).sum();
            System.out.println("Total old gc time consumed " + oldTimeTotal);
            System.out.println("Avg old gc time consumed " + oldTimeTotal/oldGcInfosCopy.size());

            var gcTimeTotal = youngTimeTotal + oldTimeTotal;
            System.out.println("Total gc time consumption: " + gcTimeTotal);
            var totalCount = youngCount + oldCount;
            System.out.println("Total count: " +totalCount);

            long endTime   = System.nanoTime();
            long totalTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
            System.out.println("Total time: " + totalTime);
            System.out.println("Total count: " + totalTime);
            System.out.println("young/old: " + ((double)youngTimeTotal)/((double)oldTimeTotal));
            System.out.println("gc part: " + ((double)gcTimeTotal)/((double)totalTime));

            System.out.println("Done!");

            System.out.println("<------------------------------------------>");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    static void installGCMonitoring() {
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            NotificationListener listener;
            System.out.println("GC bean name is " + gcBean.getName());
            if (Stream.of(YoungGenNames.values()).anyMatch(youngGenName -> youngGenName.getNameAsString().equals(gcBean.getName()))) {
                listener = (notification, handback) -> {
                    if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                        GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                        if (!Benchmark.youngCounted) {
                            youngGcInfos.add(info.getGcInfo());
                            Benchmark.youngCounted  = true;
                        }
                    }
                };
            } else if (Stream.of(OldGenNames.values()).anyMatch(oldGenName -> oldGenName.getNameAsString().equals(gcBean.getName()))) {
                listener = (notification, handback) -> {
                    if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                        GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                        if (!Benchmark.oldCounted) {
                            oldGcInfos.add(info.getGcInfo());
                            Benchmark.oldCounted = true;
                        }
                    }
                };
            } else {
                throw new IllegalStateException("No such GC name!");
            }
            emitter.addNotificationListener(listener, null, null);
        }
    }

    private enum YoungGenNames {
        COPY("Copy"),
        PS_SCANVENGE("PS Scavenge"),
        PAR_NEW("ParNew"),
        G1_YOUNG("G1 Young Generation");

        private String nameAsString;

        YoungGenNames(String nameAsString) {
            this.nameAsString = nameAsString;
        }

        public String getNameAsString() {
            return this.nameAsString;
        }
    }

    private enum OldGenNames {
        COPY("MarkSweepCompact"),
        PS_MARK_SWEEP("PS MarkSweep"),
        CONCURRENT_MARK_SWEEP("ConcurrentMarkSweep"),
        G1_OLD("G1 Old Generation");

        private String nameAsString;

        OldGenNames(String nameAsString) {
            this.nameAsString = nameAsString;
        }

        public String getNameAsString() {
            return this.nameAsString;
        }
    }

}
