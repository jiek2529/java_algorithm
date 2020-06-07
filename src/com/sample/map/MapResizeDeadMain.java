package com.sample.map;

/**
 * Created by jiek on 2020/6/7.
 * <p>
 * 验证 Map 扩容时导致死循环的场景
 *
 * 死锁发生在 JDK7的 HashMap#resize()->#transfer中的 do while中。
 * 执行的是头插入法扩容。扩容的前的链顺序，到扩容后成的反过来的指向现象。
 * 当 T1执行到 Entry<K,V> next = e.next;  线程被挂起时，被T2先扩容完，再执行 T1剩余流程，此时此链为环了，而产生死循环。
 */
public class MapResizeDeadMain {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new ResizeMapRunnable(), "t_"+i).start();
        }
    }
}
