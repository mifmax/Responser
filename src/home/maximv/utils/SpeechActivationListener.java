package home.maximv.utils;

/**
* receive results from a {@link SpeechActivator}
* @author Greg Milette &#60;<a href="mailto:gregorym@gmail.com">gregorym@gmail.com</a>&#62;
*/
public interface SpeechActivationListener
{
    public void activated(boolean success);
}