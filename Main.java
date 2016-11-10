public class Main {

    public static void main(String args[])
    {
        // the Calculate class converts a standard expression to postfix notation
        // allowed chars in string '(', ')',[0-9] numbers, '+', '-', '*', '/' , 'x'

        Calculate duder = new Calculate();
        //the compute function can also have a variable "x", and it's value can be specified in the second parameter
        duder.compute("(96/3)*4-4",0);



        System.out.println(duder.commands);
    }


}
