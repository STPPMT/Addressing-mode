import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

class addressingMode {

    static int search(ArrayList<String> mat, int n, String x) {
        int sum = -1;
        for (int i = 0; i < n; i++) {
            if (mat.get(i).equals(x)) {
                sum = i;
            }
        }
        return sum;
    }

    static int searchMem(String mat[], int n, String x) {
        int sum = -1;
        for (int i = 0; i < n; i++) {
            if (mat[i].equals(x)) {
                sum = i;
            }
        }
        return sum;
    }

    static int searchCom(String text, int n) {
        int sum = -1;
        for (int i = 0; i < n; i++) {
            if (',' == text.charAt(i)) {
                sum = i;
            }
        }
        return sum;
    }

    static void getMov(ArrayList<String> data1, ArrayList<String> data2, String sumt1, String s) {
        int index1 = search(data1, data1.size(), sumt1);
        System.out.println(sumt1);
        if (index1 < 0) {
            data1.add(sumt1);
            data2.add(s);
        } else {
            data1.remove(index1);
            data1.add(index1, sumt1);
            data2.remove(index1);
            data2.add(index1, s);
        }
    }

    static String sethasMOV(ArrayList<String> data1, ArrayList<String> data2, String sumt1, String sumt2, String nmem[],
            String mem[]) {
        if (sumt1.equals("stack")) {
            try {
                int nbe = Integer.parseInt(sumt2);
                mem[mem.length - 1] = mem[mem.length - 1].replace(mem[mem.length - 1], "");
                mem[mem.length - 1] = sumt2;
            } catch (Exception e) {
                System.out.println("Error");
            }
        } else {
            int id = searchMem(nmem, nmem.length, sumt1);
            if (id >= 0) {
                try {
                    int nbe = Integer.parseInt(sumt2);
                    mem[id] = mem[id].replace(mem[id], "");
                    mem[id] = sumt2;
                } catch (Exception e) {
                    System.out.println("Error");
                }
            } else {
                try {
                    int nbe = Integer.parseInt(sumt2);
                    getMov(data1, data2, sumt1, sumt2);
                } catch (Exception e) {
                    System.out.println("Error");
                }
            }
        }
        return sumt2;
    }

    static void setaddMOV(ArrayList<String> data1, ArrayList<String> data2, String sumt1, String sumt2, String nmem[],
            String mem[]) {
        int room1 = search(data1, data1.size(), sumt1);// A room1
        int room2 = searchMem(nmem, nmem.length, sumt1);// A room2

        int room11 = search(data1, data1.size(), sumt2);// B room1
        int room22 = searchMem(nmem, nmem.length, sumt2);// B room2
        try {
            int nber = Integer.parseInt(sumt1);
            if (room2 >= 0 && room11 >= 0) {
                int sum = searchMem(nmem, nmem.length, data2.get(room11));
                mem[room2] = mem[room2].replace(mem[room2], "");
                mem[room2] = mem[sum];
            } else if (room2 >= 0 && room22 >= 0) {
                mem[room2] = mem[room2].replace(mem[room2], "");
                mem[room2] = mem[room22];
            }
        } catch (Exception e) {
            if (room1 >= 0 && room11 >= 0) {
                int sum = searchMem(nmem, nmem.length, data2.get(room11));
                data2.remove(room1);
                data2.add(room1, mem[sum]);
            } else if (room1 >= 0 && room22 >= 0) {
                data2.remove(room1);
                data2.add(room1, mem[room22]);
            } else if (room1 < 0 && room11 >= 0) {
                int sum = searchMem(nmem, nmem.length, data2.get(room11));
                data1.add(sumt1);
                data2.add(mem[sum]);
            } else if (room1 < 0 && room22 >= 0) {
                data1.add(sumt1);
                data2.add(mem[room22]);
            }
        }
    }

    static String[] getText(String text2) {
        int ex = 0;
        char c;
        String sumt1 = "", sumt2 = "";
        for (int i = 0; i < text2.length(); i++) {
            c = text2.charAt(i);
            String sum = String.valueOf(c);

            if (sum.equals(",")) {
                if (ex == 1) {
                    sumt2 = sumt2 + sum;
                    continue;
                }
                ex++;
                continue;
            }
            if (ex == 0) {
                sumt1 = sumt1 + sum;
            }
            if (ex > 0) {
                sumt2 = sumt2 + sum;
            }
        }
        return new String[] { sumt1, sumt2 };
    }

    static void setmemory(String nmem[], String mem[]) {
        System.out.println("\t Main Memory\n");
        System.out.print("(TOS)");
        for (int i = nmem.length - 1; i >= 0; i--) {
            System.out.println("\t" + nmem[i] + "\t| " + mem[i]);
        }
    }

    static void asmd(ArrayList<String> data1, ArrayList<String> data2, String sumt1, String sumt2, String nmem[],
            String mem[], String text1) {

        String nohas = sumt2.replace("#", "");
        try {
            int num2 = Integer.parseInt(nohas);
            try {
                int numm1 = Integer.parseInt(sumt1);
                int n = searchMem(nmem, nmem.length, sumt1);
                if (n >= 0) {
                    int num1 = Integer.parseInt(mem[n]);
                    mem[n] = mem[n].replace(mem[n], "");
                    int sum = 0;
                    if (text1.equals("ADD") || text1.equals("add")) {
                        sum = num1 + num2;
                    } else if (text1.equals("SUB") || text1.equals("sub")) {
                        sum = num1 - num2;
                    } else if (text1.equals("MUL") || text1.equals("mul")) {
                        sum = num1 * num2;
                    } else if (text1.equals("DIV") || text1.equals("div")) {
                        sum = num1 / num2;
                    }
                    String s = String.valueOf(sum);
                    mem[n] = s;
                } else {
                    System.out.println(sumt1 + " Not in memory");
                }
            } catch (Exception e) {
                int n = search(data1, data1.size(), sumt1);
                if (n >= 0) {
                    int num1 = Integer.parseInt(data2.get(n));
                    data2.remove(n);
                    int sum = 0;
                    if (text1.equals("ADD") || text1.equals("add")) {
                        sum = num1 + num2;
                    } else if (text1.equals("SUB") || text1.equals("sub")) {
                        sum = num1 - num2;
                    } else if (text1.equals("MUL") || text1.equals("mul")) {
                        sum = num1 * num2;
                    } else if (text1.equals("DIV") || text1.equals("div")) {
                        sum = num1 / num2;
                    }
                    String s = String.valueOf(sum);
                    data2.add(n, s);
                }
            }
        } catch (Exception e) {
            System.out.println(nohas + " Not a Constant");
        }
    }

    static void setaddasmd(ArrayList<String> data1, ArrayList<String> data2, String sumt1, String sumt2, String nmem[],
            String mem[], String text1) {
        int room1 = search(data1, data1.size(), sumt1);// A room1
        int room2 = searchMem(nmem, nmem.length, sumt1);// A room2

        int room11 = search(data1, data1.size(), sumt2);// B room1
        int room22 = searchMem(nmem, nmem.length, sumt2);// B room2
        try {
            int nber1 = Integer.parseInt(sumt1);
            if (room2 >= 0 && room11 >= 0) {
                int num1 = Integer.parseInt(mem[room2]);
                int id = searchMem(nmem, nmem.length, data2.get(room11));
                int num2 = Integer.parseInt(mem[id]);
                int sum1 = 0;
                if (text1.equals("ADD") || text1.equals("add")) {
                    sum1 = num1 + num2;
                } else if (text1.equals("SUB") || text1.equals("sub")) {
                    sum1 = num1 - num2;
                } else if (text1.equals("MUL") || text1.equals("mul")) {
                    sum1 = num1 * num2;
                } else if (text1.equals("DIV") || text1.equals("div")) {
                    sum1 = num1 / num2;
                }
                mem[room2] = mem[room2].replace(mem[room2], "");
                String s = String.valueOf(sum1);
                mem[room2] = s;
            } else if (room2 >= 0 && room22 >= 0) {
                int num1 = Integer.parseInt(mem[room2]);
                int num2 = Integer.parseInt(mem[room22]);
                int sum1 = 0;
                if (text1.equals("ADD") || text1.equals("add")) {
                    sum1 = num1 + num2;
                } else if (text1.equals("SUB") || text1.equals("sub")) {
                    sum1 = num1 - num2;
                } else if (text1.equals("MUL") || text1.equals("mul")) {
                    sum1 = num1 * num2;
                } else if (text1.equals("DIV") || text1.equals("div")) {
                    sum1 = num1 / num2;
                }
                mem[room2] = mem[room2].replace(mem[room2], "");
                String s = String.valueOf(sum1);
                mem[room2] = s;
            }
        } catch (Exception e) {
            if (room1 >= 0 && room11 >= 0) {
                int num1 = Integer.parseInt(data2.get(room1));
                int id = searchMem(nmem, nmem.length, data2.get(room11));
                int num2 = Integer.parseInt(mem[id]);
                int sum1 = 0;
                if (text1.equals("ADD") || text1.equals("add")) {
                    sum1 = num1 + num2;
                } else if (text1.equals("SUB") || text1.equals("sub")) {
                    sum1 = num1 - num2;
                } else if (text1.equals("MUL") || text1.equals("mul")) {
                    sum1 = num1 * num2;
                } else if (text1.equals("DIV") || text1.equals("div")) {
                    sum1 = num1 / num2;
                }
                data2.remove(room1);
                String s = String.valueOf(sum1);
                data2.add(room1, s);
            } else if (room1 >= 0 && room22 >= 0) {
                int num1 = Integer.parseInt(data2.get(room1));
                int num2 = Integer.parseInt(mem[room22]);
                int sum1 = 0;
                if (text1.equals("ADD") || text1.equals("add")) {
                    sum1 = num1 + num2;
                } else if (text1.equals("SUB") || text1.equals("sub")) {
                    sum1 = num1 - num2;
                } else if (text1.equals("MUL") || text1.equals("mul")) {
                    sum1 = num1 * num2;
                } else if (text1.equals("DIV") || text1.equals("div")) {
                    sum1 = num1 / num2;
                }
                data2.remove(room1);
                String s = String.valueOf(sum1);
                data2.add(room1, s);
            } else if (room1 < 0 && room11 >= 0) {
                System.out.println(sumt1 + " Not in memory");
            } else if (room1 < 0 && room22 >= 0) {
                System.out.println(sumt1 + " Not in memory");
            }
        }
    }

    static String all(String mode, char c, char c1, ArrayList<String> data1, ArrayList<String> data2, String sumt1,
            String sumt2, String nmem[], String mem[], String text1) {
        if (c == '#') {
            asmd(data1, data2, sumt1, sumt2, nmem, mem, text1);
            mode = "Immediate Addressing";
        }else {
            setMOVasmd(text1, data1, data2, sumt1, sumt2, nmem, mem);
            mode = "Direct Addressing";
        }
        if (c == '@') {
            String nohas = sumt2.replace("@", "");
            setaddasmd(data1, data2, sumt1, nohas, nmem, mem, text1);
            mode = "Indirect Addressing";
        }
        if (c == '[' && c1 == ']') {
            String noa = sumt2.replace("[", "");
            noa = noa.replace("]", "");
            int id = searchCom(noa, noa.length());
            if (id < 0) {
                setaddasmd(data1, data2, sumt1, noa, nmem, mem, text1);
                mode = "Register Idirect Addressing";
            } else {
                String tx[] = getText(noa);
                String tx1 = tx[0];
                String tx2 = tx[1];
                if (tx1.equals("SI") || tx1.equals("DI")) {
                    int i1 = search(data1, data1.size(), tx1);
                    int num1 = Integer.parseInt(data2.get(i1));
                    int num2 = Integer.parseInt(tx2);
                    int sum = num1 + num2;
                    String s = String.valueOf(sum);
                    setaddasmd(data1, data2, sumt1, s, nmem, mem, text1);
                    mode = "Relative Addressing";
                }
                if (tx1.equals("BP") || tx1.equals("BX")) {
                    int i1 = search(data1, data1.size(), tx1);
                    int num1 = Integer.parseInt(data2.get(i1));
                    int num2 = Integer.parseInt(tx2);
                    int sum = num1 + num2;
                    String s = String.valueOf(sum);
                    setaddasmd(data1, data2, sumt1, s, nmem, mem, text1);
                    mode = "Relative Addressing";
                }
            }
        }
        if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8'
                || c == '9') {
                    if (sumt2.equals("stack")) {
                        Stack(text1,data1, data2, sumt1, mem[9], nmem, mem);
                        mode = "Stack Addressing";
                    } else {
                        setaddasmd(data1, data2, sumt1, sumt2, nmem, mem, text1);
                        mode = "Direct Addressing";}
        } 
        else {
            if (sumt2.equals("stack")) {
                Stack(text1,data1, data2, sumt1, mem[9], nmem, mem);
                mode = "Stack Addressing";
            } }
        return mode;
    }

    static void setAmovB(ArrayList<String> data1, ArrayList<String> data2, String sumt1, String sumt2, String nmem[],
            String mem[]) {
        int room1 = search(data1, data1.size(), sumt1);// A room1;
        int room11 = search(data1, data1.size(), sumt2);// B room1;
        if (room1 >= 0 && room11 >= 0) {
            String sum = data2.get(room11);
            data2.remove(room1);
            data2.add(room1, sum);
        } else if (room1 < 0 && room11 >= 0) {
            data1.add(sumt1);
            data2.add(data2.get(room11));
        }
    }
    static void setStack(ArrayList<String> data1, ArrayList<String> data2, String sumt1, String sumt2, String nmem[],
            String mem[]) {
        int room1 = search(data1, data1.size(), sumt1);// A room1;
        if (room1 >= 0) {
            data2.remove(room1);
            data2.add(room1, mem[9]);
        } else if (room1 < 0) {
            data1.add(sumt1);
            data2.add(mem[9]);
        }
    }
    static void Stack(String text1,ArrayList<String> data1, ArrayList<String> data2, String sumt1, String sumt2, String nmem[],
            String mem[]) {
        int room1 = search(data1, data1.size(), sumt1);// A room1;

        if (room1 >= 0) {
            int num1 = Integer.parseInt(data2.get(room1));
            int num2 = Integer.parseInt(mem[9]);
            int sum1 = 0;
            if (text1.equals("ADD") || text1.equals("add")) {
                sum1 = num1 + num2;
            } else if (text1.equals("SUB") || text1.equals("sub")) {
                sum1 = num1 - num2;
            } else if (text1.equals("MUL") || text1.equals("mul")) {
                sum1 = num1 * num2;
            } else if (text1.equals("DIV") || text1.equals("div")) {
                sum1 = num1 / num2;
            }
            String s = String.valueOf(sum1);
            data2.remove(room1);
            data2.add(room1, s);
        } else if (room1 < 0) {
            data1.add(sumt1);
            data2.add(mem[9]);
        }
    }


    static void setMOVasmd(String text1, ArrayList<String> data1, ArrayList<String> data2, String sumt1, String sumt2,
            String nmem[], String mem[]) {
        int room1 = search(data1, data1.size(), sumt1);// A room1;
        int room11 = search(data1, data1.size(), sumt2);// B room1;
        if (room1 >= 0 && room11 >= 0) {
            String sum = data2.get(room11);
            int num1 = Integer.parseInt(data2.get(room1));
            int num2 = Integer.parseInt(sum);
            int sum1 = 0;
            if (text1.equals("ADD") || text1.equals("add")) {
                sum1 = num1 + num2;
            } else if (text1.equals("SUB") || text1.equals("sub")) {
                sum1 = num1 - num2;
            } else if (text1.equals("MUL") || text1.equals("mul")) {
                sum1 = num1 * num2;
            } else if (text1.equals("DIV") || text1.equals("div")) {
                sum1 = num1 / num2;
            }
            String s = String.valueOf(sum1);
            data2.remove(room1);
            data2.add(room1, s);
        } else if (room1 < 0 && room11 >= 0) {
            System.out.println(sumt1 + " Not in memory");
        }
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String text1, text2, sumt1 = "", sumt2 = "", mode = "";
        char c, c1;
        ArrayList<String> data1 = new ArrayList<String>();
        ArrayList<String> data2 = new ArrayList<String>();
        String nmem[] = new String[] { "10", "20", "30", "40", "50", "60", "70", "80", "90", "100" };
        String mem[] = new String[10];
        for (int i = 0; i < mem.length; i++) {
            int mes = (int) (Math.random() * 101) + 0;
            mem[i] = String.valueOf(mes);
        }
        System.out.println("                      \"How to fill out information\"                          ");
        System.out.println("                     --------------------------------                          ");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("-       Operrator ---->    MOV   ADD     SUB    DIV    LOAD    MUL             -");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("-        Input Example             |                  Addressing               -");
        System.out.println("-       --------------             |                 ------------              -");
        System.out.println("-      ---->   MOV A1,#100         |     #    =  (Immediate Addressing)        -");
        System.out.println("-                                  |                                           -");
        System.out.println("-      ---->   ADD 40,stack        |   stack  =  (Stack Addressing)            -");
        System.out.println("-                                  |                                           -");
        System.out.println("-      ---->   SUB R3,@R1          |    @     =  (Indirect Addressing)         -");
        System.out.println("-                                  |                                           -");
        System.out.println("-      ---->   DIV R2,R3           |  R2,R3   =  (Register Direct Addressing)  -");
        System.out.println("-                                  |                                           -");
        System.out.println("-      ---->   LOAD R4,60          |  R4,60   =  (Direct Addressing)           -");
        System.out.println("-                                  |                                           -");
        System.out.println("-      ---->   MUL AL,[BP,2]       |   [BP,2] =  (Relative Addressing)         -");
        System.out.println("-                                  |                                           -");
        System.out.println("-      ---->   LOAD AL,[SI,2]      |  [SI,2]  =  (Indexed Addressing)          -");
        System.out.println("-                                  |                                           -");
        System.out.println("-      ---->   LOAD [BX]           |   [BX]   =  (Register Indirect Addressing)-");
        System.out.println("-                                  |                                           -");
        System.out.println("--------------------------------------------------------------------------------\n");
        System.out.print("Input SI:");
        String o = scn.next();
        data1.add("SI");
        data2.add(o);
        System.out.print("Input DI:");
        o = scn.next();
        data1.add("DI");
        data2.add(o);
        System.out.print("Input BX:");
        o = scn.next();
        data1.add("BX");
        data2.add(o);
        System.out.print("Input BP:");
        o = scn.next();
        data1.add("BP");
        data2.add(o);
        System.out.println("\n=====================================");
        setmemory(nmem, mem);
        System.out.println("\n=====================================");
        for (text1 = scn.next(), text2 = scn.next(); true; text1 = scn.next(), text2 = scn.next()) {
            String mess[] = getText(text2);
            sumt1 = mess[0];
            sumt2 = mess[1];

            c = sumt2.charAt(0);
            c1 = sumt2.charAt(sumt2.length() - 1);
            // ========================================================/ MOV
            // /======================================================
            if (text1.equals("MOV") || text1.equals("mov") || text1.equals("LOAD") || text1.equals("load")) {
                if (c == '#') {
                    String nohas = sumt2.replace("#", "");
                    sethasMOV(data1, data2, sumt1, nohas, nmem, mem);
                    mode = "Immediate Addressing";
                }else {
                    setAmovB(data1, data2, sumt1, sumt2, nmem, mem);
                    mode = "Direct Addressing";
                }
                if (c == '@') {
                    String nohas = sumt2.replace("@", "");
                    setaddMOV(data1, data2, sumt1, nohas, nmem, mem);
                    mode = "Indirect Addressing";
                }
                if (c == '[' && c1 == ']') {
                    String noa = sumt2.replace("[", "");
                    noa = noa.replace("]", "");
                    int id = searchCom(noa, noa.length());
                    if (id < 0) {
                        setaddMOV(data1, data2, sumt1, noa, nmem, mem);
                    } else {
                        String tx[] = getText(noa);
                        String tx1 = tx[0];
                        String tx2 = tx[1];
                        if (tx1.equals("SI") || tx1.equals("DI")) {
                            int i1 = search(data1, data1.size(), tx1);
                            int num1 = Integer.parseInt(data2.get(i1));
                            int num2 = Integer.parseInt(tx2);
                            int sum = num1 + num2;
                            String s = String.valueOf(sum);
                            setaddMOV(data1, data2, sumt1, s, nmem, mem);
                            mode = "Relative Addressing";
                        }
                        if (tx1.equals("BP") || tx1.equals("BX")) {
                            int i1 = search(data1, data1.size(), tx1);
                            int num1 = Integer.parseInt(data2.get(i1));
                            int num2 = Integer.parseInt(tx2);
                            int sum = num1 + num2;
                            String s = String.valueOf(sum);
                            setaddMOV(data1, data2, sumt1, s, nmem, mem);
                            mode = "Relative Addressing";
                        }
                    }
                }
                if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7'
                        || c == '8' || c == '9') {
                    if (sumt2.equals("stack")) {
                        setStack(data1, data2, sumt1, mem[9], nmem, mem);
                        mode = "Stack Addressing";
                    } else {
                        setaddMOV(data1, data2, sumt1, sumt2, nmem, mem);
                        mode = "Direct Addressing";
                    }
                } else {
                    if (sumt2.equals("stack")) {
                        setStack(data1, data2, sumt1, mem[9], nmem, mem);
                        mode = "Stack Addressing";
                    } 
                }
            }
            // ==========================================================/ ADD
            // /============================================================
            if (text1.equals("ADD")) {
                mode=all(mode, c, c1, data1, data2, sumt1, sumt2, nmem, mem, text1);
            }
            // ==========================================================/ SUB
            // /============================================================
            if (text1.equals("SUB")) {
                mode=all(mode, c, c1, data1, data2, sumt1, sumt2, nmem, mem, text1);
            }
            // ==========================================================/ MUL
            // /============================================================
            if (text1.equals("MUL")) {
                mode=all(mode, c, c1, data1, data2, sumt1, sumt2, nmem, mem, text1);
            }
            // ==========================================================/ DIV
            // /============================================================
            if (text1.equals("DIV")) {
                mode=all(mode, c, c1, data1, data2, sumt1, sumt2, nmem, mem, text1);
            }
            // =============================================================================================================================
            System.out.println("\n=====================================");
            setmemory(nmem, mem);
            System.out.println("\n=====================================");
            System.out.println("\nRegister\tResgiser Value");
            for (int i = 0; i < data1.size(); i++) {
                System.out.println(data1.get(i) + "\t\t " + data2.get(i));
            }
            int i = search(data1, data1.size(), sumt1);
            int i1 = searchMem(nmem, nmem.length, sumt1);
            System.out.println("\n=====================================");
            if (i >= 0 && i >= 0) {
                System.out.println(data1.get(i) + "=" + data2.get(i) + "-->" + mode);
            } else {
                if (sumt1.equals("stack")) {
                    System.out.println("\n=====================================");
                    System.out.println(sumt1 + "=" + mem[9] + "--> Stack Addressing");
                    System.out.println("\n=====================================");
                    continue;
                }
                System.out.println("\n=====================================");
                if(i1<0){
                    continue;
                }
                System.out.println(sumt1 + "=" + mem[i1] + "-->" + mode);
                System.out.println("\n=====================================");
                continue;
            }
            System.out.println("\n=====================================");
            sumt1 = sumt1.replace(sumt1, "");
            sumt2 = sumt2.replace(sumt2, "");
        }
    }

}