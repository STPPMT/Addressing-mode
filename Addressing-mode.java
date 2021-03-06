import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

class coa {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String text1, text2, sumt1 = "", sumt2 = "", mode = "", o;
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
        o = scn.next();
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
        setmemory(nmem, mem);
        for (text1 = scn.next(), text2 = scn.next(); true; text1 = scn.next(), text2 = scn.next()) {
            String mess[] = getText(text2);
            sumt1 = mess[0];
            sumt2 = mess[1];
            c = sumt2.charAt(0);
            c1 = sumt2.charAt(sumt2.length() - 1);
            int sx1 = search(data1, data1.size(), sumt1);
            int sx2 = search(data1, data1.size(), sumt2);
            int sx3 = searchMem(nmem, nmem.length, sumt1);
            int sx4 = searchMem(nmem, nmem.length, sumt2);
            // ========================================================/ MOV
            // /======================================================
            if (text1.equals("MOV") || text1.equals("mov") || text1.equals("LOAD") || text1.equals("load")) {
                if (c == '#') {
                    String nohas = sumt2.replace("#", "");
                    String re = sethasMOV(data1, data2, sumt1, nohas, nmem, mem);
                    if (sumt1.equals("stack") && (sx2 >= 0 || sx4 >= 0)) {
                        if (re.equals("e")) {
                            mode = "ERROR!";
                        } else {
                            mode = "Immediate Addressing";
                        }
                        output(data1, data2, nmem, mem, sumt1, nohas, mode);
                        continue;
                    }
                    if (re.equals("e")) {
                        mode = "ERROR!";
                    } else {
                        mode = "Immediate Addressing";
                    }
                    output(data1, data2, nmem, mem, sumt1, nohas, mode);
                    continue;
                }
                if (c == '@') {
                    String nohas = sumt2.replace("@", "");
                    int n = searchMem(nmem, nmem.length, nohas);
                    int m = search(data1, data1.size(), nohas);
                    if (sumt1.equals("stack")) {
                        mode = "Indirect Addressing";
                        allst(data1, data2, nmem, mem, sumt1, mem[n], mode);
                        continue;
                    }
                    if (n > -1 || m > -1) {
                        setaddMOV(data1, data2, sumt1, nohas, nmem, mem);
                        mode = "Indirect Addressing";
                        int s = searchMem(nmem, nmem.length, nohas);
                        output(data1, data2, nmem, mem, sumt1, mem[s], mode);
                        continue;
                    } else {
                        mode = "Not in memory";
                        System.out.println(nohas + " --> " + mode);
                        continue;
                    }
                }
                if (c == '[' && c1 == ']') {
                    String noa = sumt2.replace("[", "");
                    noa = noa.replace("]", "");
                    int id = searchCom(noa, noa.length());
                    if (id < 0) {
                        mode = "Register Indirect Addressing";
                        int a = search(data1, data1.size(), noa);
                        int s = searchMem(nmem, nmem.length, data2.get(a));
                        if (sumt1.equals("stack")) {
                            allst(data1, data2, nmem, mem, sumt1, mem[s], mode);
                            continue;
                        }
                        setaddMOV(data1, data2, sumt1, noa, nmem, mem);
                        output(data1, data2, nmem, mem, sumt1, mem[s], mode);
                        continue;
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
                            int s1 = searchMem(nmem, nmem.length, s);
                            mode = "Indexed Addressing";
                            if (sumt1.equals("stack")) {
                                allst(data1, data2, nmem, mem, sumt1, mem[s1], mode);
                                continue;
                            }
                            setaddMOV(data1, data2, sumt1, s, nmem, mem);
                            output(data1, data2, nmem, mem, sumt1, mem[s1], mode);
                            continue;
                        }
                        if (tx1.equals("BP") || tx1.equals("BX")) {
                            int i1 = search(data1, data1.size(), tx1);
                            int num1 = Integer.parseInt(data2.get(i1));
                            int num2 = Integer.parseInt(tx2);
                            int sum = num1 + num2;
                            String s = String.valueOf(sum);
                            int s1 = searchMem(nmem, nmem.length, s);
                            mode = "Relative Addressing";
                            if (sumt1.equals("stack")) {
                                allst(data1, data2, nmem, mem, sumt1, mem[s1], mode);
                                continue;
                            }
                            setaddMOV(data1, data2, sumt1, s, nmem, mem);
                            output(data1, data2, nmem, mem, sumt1, mem[s1], mode);
                            continue;
                        }
                    }
                }
                if ((sx1 == -1 || sx1 >= 0) && sumt2.equals("stack")) {
                    setStack(data1, data2, sumt1, mem[9], nmem, mem);
                    mode = "Stack Addressing";
                    output(data1, data2, nmem, mem, sumt1, mem[9], mode);
                    continue;
                }

                if (sx1 >= 0 && sx2 >= 0) {
                    setAmovB(data1, data2, sumt1, sumt2, nmem, mem);
                    mode = "Register Direct Addressing";
                    int a = search(data1, data1.size(), sumt2);
                    output(data1, data2, nmem, mem, sumt1, data2.get(a), mode);
                    continue;
                }
                if ((sx1 == -1 || sx1 >= 0) && sx4 >= 0) {
                    setaddMOV(data1, data2, sumt1, sumt2, nmem, mem);
                    mode = "Direct Addressing";
                    int s = searchMem(nmem, nmem.length, sumt2);
                    output(data1, data2, nmem, mem, sumt1, mem[s], mode);
                    continue;
                } else {
                    System.out.print(sumt2 + " Not in memory");
                }
            }
            // ==========================================================/ ADD
            // /============================================================
            if (text1.equals("ADD")) {
                all(mode, c, c1, data1, data2, sumt1, sumt2, nmem, mem, text1, sx1, sx2, sx4);
                continue;
            }
            // ==========================================================/ SUB
            // /============================================================
            if (text1.equals("SUB")) {
                all(mode, c, c1, data1, data2, sumt1, sumt2, nmem, mem, text1, sx1, sx2, sx4);
                continue;
            }
            // ==========================================================/ MUL
            // /============================================================
            if (text1.equals("MUL")) {
                all(mode, c, c1, data1, data2, sumt1, sumt2, nmem, mem, text1, sx1, sx2, sx4);
                continue;
            }
            // ==========================================================/ DIV
            // /============================================================
            if (text1.equals("DIV")) {
                all(mode, c, c1, data1, data2, sumt1, sumt2, nmem, mem, text1, sx1, sx2, sx4);
                continue;
            }
            sumt1 = sumt1.replace(sumt1, "");
            sumt2 = sumt2.replace(sumt2, "");
        }
    }

    static void allst(ArrayList<String> data1, ArrayList<String> data2, String nmem[], String mem[], String sumt1,
            String sumt2, String mode) {
        if (sumt1.equals("stack")) {
            if (sumt1.equals("stack")) {
                sethasMOV(data1, data2, sumt1, sumt2, nmem, mem);
                output(data1, data2, nmem, mem, sumt1, sumt2, mode);
            } else {
                mode = "Not in memory";
                System.out.println(sumt2 + " --> " + mode);
            }
        }
    }

    static void allstasdm(ArrayList<String> data1, ArrayList<String> data2, String nmem[], String mem[], String sumt1,
            String sumt2, String mode, String text1) {
        if (sumt1.equals("stack")) {
            if (sumt1.equals("stack")) {
                asmd(data1, data2, sumt1, sumt2, nmem, mem, text1);
                output(data1, data2, nmem, mem, sumt1, sumt2, mode);
            } else {
                mode = "Not in memory";
                System.out.println(sumt2 + " --> " + mode);
            }
        }
    }

    static void output(ArrayList<String> data1, ArrayList<String> data2, String nmem[], String mem[], String m1,
            String m2, String mode) {
        setmemory(nmem, mem);
        System.out.println("\nRegister\tResgiser Value");
        for (int i = 0; i < data1.size(); i++) {
            System.out.println(data1.get(i) + "\t\t " + data2.get(i));
        }
        System.out.println("\n=====================================");
        System.out.println(m1 + " = " + m2 + " --> " + mode);
        System.out.println("\n=====================================");

    }

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
        String mode = "";
        if (sumt1.equals("stack")) {
            try {
                Integer.parseInt(sumt2);
                mem[mem.length - 1] = mem[mem.length - 1].replace(mem[mem.length - 1], "");
                mem[mem.length - 1] = sumt2;
            } catch (Exception e) {
                mode = "e";
            }
        } else {
            int id = searchMem(nmem, nmem.length, sumt1);
            if (id >= 0) {
                try {
                    Integer.parseInt(sumt2);
                    mem[id] = mem[id].replace(mem[id], "");
                    mem[id] = sumt2;
                } catch (Exception e) {
                    mode = "e";
                }
            } else {
                try {
                    Integer.parseInt(sumt2);
                    getMov(data1, data2, sumt1, sumt2);
                } catch (Exception e) {
                    mode = "e";
                }
            }
        }
        return mode;
    }

    static void setaddMOV(ArrayList<String> data1, ArrayList<String> data2, String sumt1, String sumt2, String nmem[],
            String mem[]) {
        int room1 = search(data1, data1.size(), sumt1);// A room1
        int room2 = searchMem(nmem, nmem.length, sumt1);// A room2

        int room11 = search(data1, data1.size(), sumt2);// B room1
        int room22 = searchMem(nmem, nmem.length, sumt2);// B room2
        try {
            Integer.parseInt(sumt1);
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
        System.out.println("\n=====================================");
        System.out.println("\t Main Memory\n");
        System.out.print("(TOS)");
        for (int i = nmem.length - 1; i >= 0; i--) {
            System.out.println("\t" + nmem[i] + "\t| " + mem[i]);
        }
        System.out.println("\n=====================================");
    }

    static void asmd(ArrayList<String> data1, ArrayList<String> data2, String sumt1, String sumt2, String nmem[],
            String mem[], String text1) {

        String nohas = sumt2.replace("#", "");
        try {
            int num2 = Integer.parseInt(nohas);
            try {
                Integer.parseInt(sumt1);
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
            Integer.parseInt(sumt1);
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

    static void all(String mode, char c, char c1, ArrayList<String> data1, ArrayList<String> data2, String sumt1,
            String sumt2, String nmem[], String mem[], String text1, int sx1, int sx2, int sx4) {
        for (;;) {
            if (c == '#') {
                String nohas = sumt2.replace("#", "");
                String re = sethasMOV(data1, data2, sumt1, nohas, nmem, mem);
                asmd(data1, data2, sumt1, sumt2, nmem, mem, text1);
                break;
            }
            if (c == '@') {
                String nohas = sumt2.replace("@", "");
                int n = searchMem(nmem, nmem.length, nohas);
                int m = search(data1, data1.size(), nohas);
                if (sumt1.equals("stack") && sx4 >= 0) {
                    mode = "Indirect Addressing";
                    allstasdm(data1, data2, nmem, mem, sumt1, mem[n], mode, text1);
                    break;
                }
                if (n >= 0 || m >= 0) {
                    setaddasmd(data1, data2, sumt1, nohas, nmem, mem, text1);
                    mode = "Indirect Addressing";
                    int s = search(data1, data1.size(), sumt1);
                    output(data1, data2, nmem, mem, sumt1, data2.get(s), mode);
                    break;
                } else {
                    mode = "Not in memory";
                    System.out.println(nohas + " --> " + mode);
                    break;
                }
            }
            if (c == '[' && c1 == ']') {
                String noa = sumt2.replace("[", "");
                noa = noa.replace("]", "");
                int id = searchCom(noa, noa.length());
                if (id < 0) {
                    mode = "Register Indirect Addressing";
                    int a = search(data1, data1.size(), noa);
                    int s = searchMem(nmem, nmem.length, data2.get(a));
                    if (sumt1.equals("stack")) {
                        allstasdm(data1, data2, nmem, mem, sumt1, mem[s], mode, text1);
                        break;
                    }
                    setaddasmd(data1, data2, sumt1, noa, nmem, mem, text1);
                    output(data1, data2, nmem, mem, sumt1, mem[s], mode);
                    break;
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
                        int s1 = searchMem(nmem, nmem.length, s);
                        mode = "Indexed Addressing";
                        if (sumt1.equals("stack")) {
                            allstasdm(data1, data2, nmem, mem, sumt1, mem[s1], mode, text1);
                            break;
                        }
                        setaddasmd(data1, data2, sumt1, s, nmem, mem, text1);
                        output(data1, data2, nmem, mem, sumt1, mem[s1], mode);
                        break;
                    }
                    if (tx1.equals("BP") || tx1.equals("BX")) {
                        int i1 = search(data1, data1.size(), tx1);
                        int num1 = Integer.parseInt(data2.get(i1));
                        int num2 = Integer.parseInt(tx2);
                        int sum = num1 + num2;
                        String s = String.valueOf(sum);
                        int s1 = searchMem(nmem, nmem.length, s);
                        mode = "Relative Addressing";
                        if (sumt1.equals("stack")) {
                            allstasdm(data1, data2, nmem, mem, sumt1, mem[s1], mode, text1);
                            break;
                        }
                        setaddasmd(data1, data2, sumt1, s, nmem, mem, text1);
                        output(data1, data2, nmem, mem, sumt1, mem[s1], mode);
                        break;
                    }
                }
            }
            if (sx1 >= 0 && sumt2.equals("stack")) {
                Stack(text1, data1, data2, sumt1, mem[9], nmem, mem);
                mode = "Stack Addressing";
                output(data1, data2, nmem, mem, sumt1, mem[9], mode);
                break;
            }
            if (sx1 >= 0 && sx2 >= 0) {
                setMOVasmd(text1, data1, data2, sumt1, sumt2, nmem, mem);
                mode = "Register Direct Addressing";
                int a = search(data1, data1.size(), sumt1);
                output(data1, data2, nmem, mem, sumt1, data2.get(a), mode);
                break;
            }
            if (sx1 >= 0 && sx4 >= 0) {
                setaddasmd(data1, data2, sumt1, sumt2, nmem, mem, text1);
                mode = "Direct Addressing";
                int s = searchMem(nmem, nmem.length, sumt2);
                output(data1, data2, nmem, mem, sumt1, mem[s], mode);
                break;
            } else {
                System.out.println("Not in memory");
                break;
            }
        }

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

    static void Stack(String text1, ArrayList<String> data1, ArrayList<String> data2, String sumt1, String sumt2,
            String nmem[], String mem[]) {
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
}