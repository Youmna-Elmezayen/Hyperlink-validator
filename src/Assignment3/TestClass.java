
package Assignment3;



public class TestClass 
{
    public static void main(String args[])
    {
        String Url = "https://www.google.com";
        ThreadRun tr1 = new ThreadRun();
        tr1.LinkGetter(Url, 0, 1, 3);
    }
}
