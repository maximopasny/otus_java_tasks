Для запуска выполнить ./start.sh

<h2>Результаты выполнения домашнего задания №5</h2>

<h3>Для начала стоит описать вступительные предположения.</h3>

- Программа, которая за пять минут падает в OOM, вероятно, плохо    моделирует сценарий жизни обычного энтерпрайз-приложения.
- Нагрузка (и потребление памяти на объекты) в настоящем энтерпрайз приложении, возможно, описывается периодической функцией, а не быстрым приближением к OOM
- Тесты выполнялись на персональном компьютере, на котором в фоновом режиме была другая нагрузка. Следовательно, запуски для    разных GC-алгоритмов могли иметь разный доступ к ресурсам CPU и др.
- Программа создает лист строк, динамически расширяет его и обрезает. Было экспериментально замечено, что при приближении к OOM происходит большое количество old gen сборок мусора в рамках одной итерации описанной выше процедуры. Было решено в первом прогоне программы учитывать только первую сборку old gen и young gen в рамках каждой итерации, поскольку реальная система, в моем понимании, редко приходит в такую ситуацию (см. Oracle Java Garbage Collection Basics). Несмотря на это, результаты дз все равно могут быть сильно перекошены относительно реальной системы в сторону гораздо большего использования old gen gc. Во втором прогоне отсчеки работы gc не отсекались</br>

<h3>Результаты 1 (с фильтрацией сборок)</h3>

<h4>Serial GC</h4>

```
Youngs count: 35
Total young gc time consumed 33023
Avg young gc time consumed 943
Olds count: 7
Total old gc time consumed 82984
Avg old gc time consumed 11854
Total gc time consumption: 116007
Total count: 42
Total time: 382296
Total count: 382296
young/old: 0.3979441820109901
gc part: 0.30344811350367257
Done!
```

<h4>ParallelOldGC</h4>

```
Youngs count: 36  
Total young gc time consumed 20406  
Avg young gc time consumed 566  
Olds count: 5  
Total old gc time consumed 33529  
Avg old gc time consumed 6705  
Total gc time consumption: 53935  
Total count: 41  
Total time: 234558  
Total count: 234558  
young/old: 0.6086074741268752  
gc part: 0.22994312707304804  
Done!  
```

<h4>ParallelGC</h4>

```
Youngs count: 33  
Total young gc time consumed 17431  
Avg young gc time consumed 528  
Olds count: 5  
Total old gc time consumed 35489  
Avg old gc time consumed 7097  
Total gc time consumption: 52920  
Total count: 38  
Total time: 261257  
Total count: 261257  
young/old: 0.4911662768745245  
gc part: 0.20255916587880898  
Done!  
```

<h4>G1GC</h4>  

```
Youngs count: 35  
Total young gc time consumed 21419  
Avg young gc time consumed 611  
Olds count: 6  
Total old gc time consumed 53765  
Avg old gc time consumed 8960  
Total gc time consumption: 75184  
Total count: 41  
Total time: 305017  
Total count: 305017  
young/old: 0.39838184692643913  
gc part: 0.24649117918017685  
Done!  
```

<h4>ConcMarkSweepGC</h4>

```
Youngs count: 35  
Total young gc time consumed 26188  
Avg young gc time consumed 748  
Olds count: 7  
Total old gc time consumed 59510  
Avg old gc time consumed 8501  
Total gc time consumption: 85698  
Total count: 42  
Total time: 320017  
Total count: 320017  
young/old: 0.44006049403461606  
gc part: 0.267792023548749  
Done!  
```

<h3>Выводы 1 (с фильтрацией сборок)</h3>

Наилучшим образом себя показал алгоритм ParallelGC как с точки зрения общего времени, потраченного на сборку мусора, так и с точки зрения обеспечения минимального количества сборок во время работы.

<h3>Результаты 2 (без фильтрации сборок)</h3>

<h4>Serial GC</h4>

```
Youngs count: 68
Total young gc time consumed 38482
Avg young gc time consumed 565
Olds count: 11
Total old gc time consumed 120836
Avg old gc time consumed 10985
Total gc time consumption: 159318
Total count: 79
Total time: 381159
Total count: 381159
young/old: 0.31846469595153765
gc part: 0.4179830464451843
Done!
```

<h4>ParallelOldGC</h4>

```
Youngs count: 61
Total young gc time consumed 28637
Avg young gc time consumed 454
Olds count: 12
Total old gc time consumed 124929
Avg old gc time consumed 10410
Total gc time consumption: 153566
Total count: 73
Total time: 327538
Total count: 327538
young/old: 0.2292262004818737
gc part: 0.46884941594563073
Done!  
```

<h4>ParallelGC</h4>

```
Youngs count: 61
Total young gc time consumed 33775
Avg young gc time consumed 553
Olds count: 12
Total old gc time consumed 140744
Avg old gc time consumed 11728
Total gc time consumption: 174519
Total count: 73
Total time: 370372
Total count: 370372
young/old: 0.23997470584891717
gc part: 0.4711992267234024
Done! 
```

<h4>G1GC</h4>  

```
Youngs count: 64
Total young gc time consumed 34723
Avg young gc time consumed 542
Olds count: 12
Total old gc time consumed 130091
Avg old gc time consumed 10840
Total gc time consumption: 164814
Total count: 76
Total time: 368916
Total count: 368916
young/old: 0.2669131607874488
gc part: 0.44675210617051037
Done!
```

<h4>ConcMarkSweepGC</h4>

```
Youngs count: 52
Total young gc time consumed 35211
Avg young gc time consumed 652
Olds count: 8
Total old gc time consumed 146506
Avg old gc time consumed 18313
Total gc time consumption: 181717
Total count: 60
Total time: 397149
Total count: 397149
young/old: 0.24033827966090127
gc part: 0.457553714097228
Done!
```

<h3>Выводы 2 (без фильтрации сборок)</h3>

С точки зрения обеспечения работы системы, требовательной к минимизации числа сборок, лучшим образом показал себя алгоритм ConcMarkSweepGC. С точки зрения обеспечения минимизации общего времени на сборки наилучшим образом показал себя алгоритм SerialGC.
