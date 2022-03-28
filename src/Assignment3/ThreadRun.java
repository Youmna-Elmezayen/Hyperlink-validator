package Assignment3;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class ThreadRun 
{

    public static ExecutorService es;
    public static int invalidLinksCount = 0;
    public static int validLinksCount = 0;

    public ThreadRun(int threadCount) 
    {
        es = Executors.newFixedThreadPool(threadCount);
    }

    public ThreadRun() 
    {

    }

    public void LinkGetter(String link, int depth, int cutOff, int threadCount) 
    {
        if (checkURL(link)) 
        {
            System.out.println("\nValid Link:" + link);
            validLinksCount++;

            if (depth == cutOff) 
            {
                return;
            }

            try 
            {
                Document url = Jsoup.connect(link).get();
                Elements patch = url.select("a[href]");
                //System.out.println("\nText:" + patch.text());
                
                String[] links = getAbsoluteLink(link);

                for (int i = 0; i < links.length; i++)
                {
                   ThreadImplementation ti = new ThreadImplementation(links[i], depth + 1, cutOff, threadCount);
                    es.execute(ti);
                }
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(ThreadRun.class.getName()).log(Level.SEVERE, null, ex);
            }

        } 
        else 
        {

            System.out.println("\nInvalid Link:" + link);
            invalidLinksCount++;

        }

    }

    public boolean checkURL(String link) 
    {
        boolean check = false;
        try 
        {
            Document url = Jsoup.connect(link).get();
            check = true;
        }
        catch (MalformedURLException e) 
        {
            return false;
        }
        catch (HttpStatusException ex) 
        {
            check = false;
        } 
        catch (IOException ex) 
        {
            check = false;
        }
        catch(IllegalArgumentException e)
        {
            check = false;
        }
        catch(Exception e)
        {
            check = false;
        }
        return check;
    }

    public String[] getAbsoluteLink(String link) throws IOException 
    {

        Document url = Jsoup.connect(link).get();

        Elements patch = url.select("a[href]");

        String validLinks[] = new String[patch.size()];
        try 
        {

            URL u = new URL(link);
            String absoluteLink;

            for (int i = 0; i < patch.size(); i++) {
                absoluteLink = (patch.get(i).attr("href"));
                String domainName = u.getProtocol() + "://" + u.getHost();
                if (!absoluteLink.startsWith("http")) 
                {

                    absoluteLink = domainName + absoluteLink;
                    validLinks[i] = absoluteLink;
                } else 
                {
                    validLinks[i] = absoluteLink;
                }
            }

        } 
        catch (IOException ex) 
        {
            System.out.println("Invalid URL");
        }
        return validLinks;
    }

}