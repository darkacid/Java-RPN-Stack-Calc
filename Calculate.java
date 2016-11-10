import java.util.Stack;
public class Calculate
{

    public String commands;
    private double x;
    //Main stack, holds the RPN notation for the commands string
    private Stack<Double> outputStack = new Stack<Double>();

    //temporary stack, holds the operators until the outputStack is filled
    private Stack<Character> opStack = new Stack<Character>();

    public String compute(String commandsP,double xP)
    {

        commands=commandsP;
        boolean done=false;
        x=xP;

        //Creating outputStack which has RPN form of the string
        for (int i = 0; i < commands.length(); i++)
        {
            // If selected item is a number, push to stack
            if (Character.isDigit(commands.charAt(i)))
            {
                getNum(i, outputStack);
                i--;
            }
            // If selected item is an operator
            else
            {

                if(commands.charAt(i)=='x'){outputStack.push(x);continue;}

                if (opStack.empty() == false)
                {
                    while (operationOrder(commands.charAt(i)) <= operationOrder(opStack.peek()))
                    {
                        if (operationOrder(opStack.peek()) == 0) {
                            opStack.push(commands.charAt(i));
                            done=true;
                            break;
                        }
                        if (operationOrder(commands.charAt(i)) == 0) {
                            opStack.push(commands.charAt(i));
                            done=true;
                            break;
                        }
                        if (operationOrder(commands.charAt(i)) == -1)
                        {
                            while(opStack.peek()!='(')
                                operationAction(opStack.pop());

                            if(opStack.peek()=='(')
                                opStack.pop();

                            done=true;
                            break;
                        }
                        operationAction(opStack.pop());
                        if (opStack.empty())
                        {
                            opStack.push(commands.charAt(i));
                            done=true;
                            break;
                        }
                    }
                    if(done==false)
                        opStack.push(commands.charAt(i));
                    done=false;
                }   else
                    opStack.push(commands.charAt(i));
            }
        }
        while (opStack.empty() == false)
        {
            if (operationOrder(opStack.peek()) != 0)
                operationAction(opStack.pop());
            else {

                opStack.pop();
            }
        }

        commands=Double.toString(outputStack.pop());
        return commands;
    }

    //Cut out the currently selected number from commands, and push to stack
    private void getNum(int i, Stack outputStack)
    {
        String numT;
        double num;

        int j = i+1;
        if(i!=commands.length()-1)
        {
            while (Character.isDigit(commands.charAt(j)) || commands.charAt(j) == '.')
            {
                if (j != commands.length())
                {
                    j++;
                    if(j==commands.length())break;
                }
                else
                    break;
            }
            numT = commands.substring(i, j);
            num = Double.parseDouble(numT);
            outputStack.push(num);
            commands = commands.substring(0, i) + commands.substring(j, commands.length());
        }
        else
        {
            numT = commands.substring(i, j);
            num = Double.parseDouble(numT);
            outputStack.push(num);
            commands = commands.substring(0, i) + commands.substring(j, commands.length());
        }
    }
    private void operationAction(Character  op)
    {
        Double var1,var2;
        var2=outputStack.pop();
        var1=outputStack.pop();
        switch(op)
        {
            case '+':outputStack.push(var1+var2);break;
            case '-':outputStack.push(var1-var2);break;
            case '*':outputStack.push(var1*var2);break;
            case '/':outputStack.push(var1/var2);break;
        }
    }

    private int operationOrder(char var)
    {
        switch(var)
        {
            case '+':return 1;
            case '-':return 1;
            case '*':return 2;
            case '/':return 2;
            case '(':return 0;
            case ')':return -1;
        }
        return 255;
    }
}