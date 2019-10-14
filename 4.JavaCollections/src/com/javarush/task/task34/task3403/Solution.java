package com.javarush.task.task34.task3403;

/* 
Разложение на множители с помощью рекурсии
*/
public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.recurse(144);
    }

    public void recurse(int n) {
        int i = 2;

        if (n <= i) {
            return;
        }
        while (true) {
            if (n % i == 0) {
                System.out.print(i + " ");
                break;
            }
            if(n == i){
                break;
            }
            i++;
        }
        recurse(n/i);

    }
}
