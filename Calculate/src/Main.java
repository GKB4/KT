import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {


        Scanner scanner = new Scanner(System.in);  // сканер консоли

        System.out.println("Консольный калькулятор (арабские или римские цифры до 10, один знак операции)");
        System.out.println("Введите арифметическую операцию");

        String string = scanner.nextLine();  // считывам в строку с консоли

        String otvet = calc (string);

        System.out.println("Ответ: " + otvet);

    }

    public static String calc (String input) throws IOException {
        int[] ar_numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; //диапозон арабских цифр
        String[] rom_numbers = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"}; //диапозон римских цифр
        String[] rom_decimal = new String[]{"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"}; //диапозон десяток римскими цифрами
        String[] rom_answers = new String[100]; //диапозон ответов римскими цифрами
        String result = new String ();

        //////////////////////////////////////////////////////////////////
        ///////////Заполнение массива ответов римскими цифрами//////////
        ////////////////////////////////////////////////////////////////
        for (int i = 0; i < 100; i++) {
            if (i <= 9) {
                //  System.out.println("i= "+ i);
                rom_answers[i] = rom_numbers[i];
                //  System.out.println("rom_answers [i]"+ rom_answers [i]);
            } else if (i > 9) {
                //  System.out.println("i=+ "+ i);
                int m = (i + 1) / 10 - 1;
                //  System.out.println("((i+1)/10)-1= "+ m);
                if (i - (i / 10) * 10 == 9) {
                    rom_answers[i] = rom_decimal[(i + 1) / 10 - 1];
                } else rom_answers[i] = rom_decimal[(i + 1) / 10 - 1] + rom_numbers[i - (i / 10) * 10];
                //  System.out.println("rom_answers [i]"+ rom_answers [i]);
            }
        }
        ////////////////////////////////////////////////////////////////////////////

        int proverka_ar = 0;  //переменные для проверки систем исчисления
        int proverka_rom = 0;

        int proverka_intervala = 0; //проверка интервала риских цифр
        ///////////////////////////////////////////////////////////////
        /////////////Проверка на системы исчисления
        //////////////////////////////////////////////////////////////
        for (String str : rom_numbers) {
            if (!(input.indexOf(str) == -1)) {
            //    System.out.println("Есть римские цифры");
                proverka_rom = 1;
            }
        }
        for (int x : ar_numbers) {
            if (!(input.indexOf(Integer.toString(x)) == -1)) {
            //    System.out.println("Есть арабские цифры");
                proverka_ar = 1;
            }
        }

        if (!(proverka_rom == 0) && !(proverka_ar == 0)) {
            throw new IOException("Используются одновременно разные системы исчисления");
        }


        int op_index = index_operation(input);  // поиск знаков операции в строке (если нет - то ноль)

        if (op_index == 0) {
            throw new IOException("Строка не является математической операцией.");
        } else if (!(op_index == 0)) {
            //Делим входную строку на составляющие
            String str1 = input.substring(0, op_index); //получаем первое число
            String str2 = input.substring(op_index + 1); // получаем второе число
            char op = input.charAt(op_index); //операция
            // проверка на наличие второго операнда
            if (!(index_operation(str1) == 0) || !(index_operation(str2) == 0)) {
                throw new IOException("Калькулятор не выполняет операции с 2 операндами");
            } else {

                ///////////////////////////////////////////////////////////////
                /////////////Если римские цифры - перевод в арабскую систему  и проверка диапозона римских цифр
                //////////////////////////////////////////////////////////////
                if (proverka_rom == 1) {
                    for (int i = 0; i < rom_numbers.length; i++) {
                        if (str1.equals(rom_numbers[i])) {
                            //    System.out.println("Число до "+str1);
                            str1 = Integer.toString(ar_numbers[i]);
                            //     System.out.println("Число после "+str1);
                            proverka_intervala++;
                        }

                        if (str2.equals(rom_numbers[i])) {
                            str2 = Integer.toString(ar_numbers[i]);
                            proverka_intervala++;
                        }

                    }
                    if (!(proverka_intervala == 2)) {
                        throw new IOException("Операция содержит символ, который выходит из допустимого диапозона");
                    }
                }

                //////////////////////////////////////////////////////////////


                ///////////////////////////////////////////////////////////////
                /////////////Проверка на интервал допустимых арабских чисел..
                //////////////////////////////////////////////////////////////
                if (proverka_ar == 1) {

                    for (int i = 0; i < ar_numbers.length; i++) {
                        if (str1.equals(Integer.toString(ar_numbers[i]))) {
                            //    System.out.println("Число до "+str1);
                            proverka_intervala++;
                        }

                        if (str2.equals(Integer.toString(ar_numbers[i]))) {

                            proverka_intervala++;
                        }

                    }
                    if (!(proverka_intervala == 2)) {
                        throw new IOException("Операция содержит символ, который выходит из допустимого диапозона");
                    }

                    //   if (Integer.parseInt(str1) > 10 || Integer.parseInt(str1) < 1) {
                    //       throw new IOException("Число 1 за пределами допустимого диапозона");
                    //   }
                    //   if (Integer.parseInt(str2) > 10 || Integer.parseInt(str2) < 1) {
                    //       throw new IOException("Число 2 за пределами допустимого диапозона");//  }
                }
                ///////////////////////////////////////////////////////////////////////


            //    System.out.println("Первое число: " + str1 + " Второе число: " + str2 + " Операция: " + op);
                int output = 0;
                if (op == '+') {
                    output = Integer.parseInt(str1) + Integer.parseInt(str2);
                } else if (op == '-') {
                    output = Integer.parseInt(str1) - Integer.parseInt(str2);
                } else if (op == '*') {
                    output = Integer.parseInt(str1) * Integer.parseInt(str2);
                } else if (op == '/') {
                    output = Integer.parseInt(str1) / Integer.parseInt(str2);
                }

                System.out.println("");

                ////////////////////////////////////////////////////
                // перевод ответа в римскую систему исчисления//
                /////////////////////////////////////////////////////
                if (proverka_rom == 1) {
                    if (!(output > 0)) {
                        throw new IOException("В римской системе исчисления нет отрицательных чисел и нуля.");
                    }
                    //System.out.println("Ответ: " + rom_answers[output - 1]);
                    result = rom_answers[output - 1];
                }
                /////////////////////////////////////////////////////////

                if (proverka_ar == 1) {
                   // System.out.println("Ответ: " + output);
                    result = Integer.toString(output);
                }
            }
        }
        return result;
    }

////////////////////////////////////////////////////////////////
    private static int index_operation(String string) {  //проверка наличия знака операции в строке, подача на выход индекса символа операции
        // Поиск символа операции в строке
        int surch_plus = string.indexOf("+");
        int surch_minus = string.indexOf("-");
        int surch_mult = string.indexOf("*");
        int surch_div = string.indexOf("/");

        int op_index=0; // индекс операции

        if(surch_plus == - 1 && surch_minus == - 1 && surch_mult == - 1 && surch_div == - 1) {
        //    System.out.println("Строка не является математической операцией");
            return 0;
        }
        else if (!(surch_plus == -1)) {
         //   System.out.println("Символ + найден в индексе: " + surch_plus);
            op_index = surch_plus;
        }
        else if (!(surch_minus == -1)) {
        //    System.out.println("Символ - найден в индексе: " + surch_minus);
            op_index = surch_minus;
        }
        else if (!(surch_mult == -1)) {
        //    System.out.println("Символ * найден в индексе: " + surch_mult);
            op_index = surch_mult;
        }
        else if (!(surch_div == -1)) {
        //    System.out.println("Символ / найден в индексе: " + surch_div);
            op_index = surch_div;
        }

        return op_index;
    }
}

