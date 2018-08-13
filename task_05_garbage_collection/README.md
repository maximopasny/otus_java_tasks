<h2>Результаты выполнения домашнего задания №5</h2>

<h3>Для начала стоит описать вступительные предположения.</h3>

- Программа, которая за пять минут падает в OOM, вероятно, плохо    моделирует сценарий жизни обычного энтерпрайз-приложения.
- Нагрузка (и потребление памяти на объекты) в настоящем энтерпрайз приложении, возможно, описывается периодической функцией, а не быстрым приближением к OOM
- Тесты выполнялись на персональном компьютере, на котором в фоновом режиме была другая нагрузка. Следовательно, запуски для    разных GC-алгоритмов могли иметь разный доступ к ресурсам CPU и др.

- Программа создает лист строк, динамически расширяет его и обрезает. Было экспериментально замечено, что при приближении к OOM происходит большое количество old gen сборок мусора в рамках одной итерации описанной выше процедуры. Было решено учитывать только первую сборку old gen и young gen в рамках каждой итерации, поскольку реальная система, в моем понимании, редко приходит в такую ситуацию (см. Oracle Java Garbage Collection Basics). Несмотря на это, результаты дз все равно могут быть сильно перекошены относительно реальной системы в сторону гораздо большего использования old gen gc</br>

<h3>Результаты</h3>

<h4>Serial GC</h4>
<br><------------------------------------------></br>
<br>Youngs count: 35</br>
<br>Total young gc time consumed 33023</br>
<br>Avg young gc time consumed 943</br>
<br>Olds count: 7</br>
<br>Total old gc time consumed 82984</br>
<br>Avg old gc time consumed 11854</br>
<br>Total gc time consumption: 116007</br>
<br>Total count: 42</br>
<br>Total time: 382296</br>
<br>Total count: 382296</br>
<br>young/old: 0.3979441820109901</br>
<br>gc part: 0.30344811350367257</br>
<br>Done!</br>
<br><------------------------------------------></br>

<h4>ParallelOldGC</h4>
<br><------------------------------------------></br>
<br>Youngs count: 36</br>
<br>Total young gc time consumed 20406</br>
<br>Avg young gc time consumed 566</br>
<br>Olds count: 5</br>
<br>Total old gc time consumed 33529</br>
<br>Avg old gc time consumed 6705</br>
<br>Total gc time consumption: 53935</br>
<br>Total count: 41</br>
<br>Total time: 234558</br>
<br>Total count: 234558</br>
<br>young/old: 0.6086074741268752</br>
<br>gc part: 0.22994312707304804</br>
<br>Done!</br>
<br><------------------------------------------></br>

<h4>ParallelGC</h4>
<br><------------------------------------------></br>
<br>Youngs count: 33</br>
<br>Total young gc time consumed 17431</br>
<br>Avg young gc time consumed 528</br>
<br>Olds count: 5</br>
<br>Total old gc time consumed 35489</br>
<br>Avg old gc time consumed 7097</br>
<br>Total gc time consumption: 52920</br>
<br>Total count: 38</br>
<br>Total time: 261257</br>
<br>Total count: 261257</br>
<br>young/old: 0.4911662768745245</br>
<br>gc part: 0.20255916587880898</br>
<br>Done!</br>
<br><------------------------------------------></br>

<h4>G1GC</h4>
<br><------------------------------------------></br>
<br>Youngs count: 35</br>
<br>Total young gc time consumed 21419</br>
<br>Avg young gc time consumed 611</br>
<br>Olds count: 6</br>
<br>Total old gc time consumed 53765</br>
<br>Avg old gc time consumed 8960</br>
<br>Total gc time consumption: 75184</br>
<br>Total count: 41</br>
<br>Total time: 305017</br>
<br>Total count: 305017</br>
<br>young/old: 0.39838184692643913</br>
<br>gc part: 0.24649117918017685</br>
<br>Done!</br>
<br><------------------------------------------></br>

<h4>ConcMarkSweepGC</h4>
<br><------------------------------------------></br>
<br>Youngs count: 35</br>
<br>Total young gc time consumed 26188</br>
<br>Avg young gc time consumed 748</br>
<br>Olds count: 7</br>
<br>Total old gc time consumed 59510</br>
<br>Avg old gc time consumed 8501</br>
<br>Total gc time consumption: 85698</br>
<br>Total count: 42</br>
<br>Total time: 320017</br>
<br>Total count: 320017</br>
<br>young/old: 0.44006049403461606</br>
<br>gc part: 0.267792023548749</br>
<br>Done!</br>
<br><------------------------------------------></br>
