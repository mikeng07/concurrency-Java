import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner;

//Find method that searches all files specified on the command line
//and prints out all line containing a reserved word
public class Find 
{

    public static void main (String[] args)
    {
        //save the first argument into reservedWord
        String reservedWord = args[0];

        //Start new thread for each file
        for (int i = 1; i<args.length; i++)
        {
            File file = new File(args[i]);
            Thread thread = new Thread(new Found(file, reservedWord));
            thread.start();

            try {
                thread.join();
            }
            catch (InterruptedException e) {}
        }
    }
}

class Found implements Runnable
{
    private File file;
    private String reservedWord;

    public Found(File file, String reservedWord)
    {
        this.file = file;
        this.reservedWord = reservedWord;
    }

    //override the run method of runnable
    public void run()
    {

        try (Scanner scanner = new Scanner(file))
        {
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();

                if(line.contains(reservedWord))
                {
                    //run 40 chars more or until EOL
                    int l = line.indexOf(reservedWord);
                    int r = Math.min(l + 40, line.length());

                    System.out.println(file.getName()+ ": "+ line.substring(l, r) );
                }
            }
        }catch (FileNotFoundException e)
        {
            System.out.println(file.getName() + " not found");
        }
    }
}