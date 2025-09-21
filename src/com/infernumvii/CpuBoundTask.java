package com.infernumvii;

public class CpuBoundTask {

    public static void task(){
        long startTime = System.currentTimeMillis();
        
        // Вычисляем числа Фибоначчи до достижения ~2 секунд
        long a = 0, b = 1;
        long count = 0;
        
        while (System.currentTimeMillis() - startTime < 1000) {
            long next = a + b;
            a = b;
            b = next;
            count++;
            
            // Периодически сбрасываем для избежания переполнения
            if (count % 1000 == 0) {
                a = 0;
                b = 1;
            }
        }
        
        System.out.println("Вычислено " + count + " чисел Фибоначчи за 2 секунды");
    }
}