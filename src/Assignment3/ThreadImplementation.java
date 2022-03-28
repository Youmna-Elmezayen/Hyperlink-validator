package Assignment3;

public class ThreadImplementation implements Runnable 
{
    private String link;
    private int depth;
    private int cutOff;
    int threadCount;

    public ThreadImplementation(String link, int depth, int cutOff, int threadCount) 
    {
        this.link = link;
        this.depth = depth;
        this.cutOff = cutOff;
        this.threadCount=threadCount;
    }

    public void run() 
    {
        ThreadRun tr = new ThreadRun();
        tr.LinkGetter(link, depth, cutOff, threadCount);
    }
    
}
