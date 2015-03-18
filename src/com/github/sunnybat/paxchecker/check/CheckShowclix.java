package com.github.sunnybat.paxchecker.check;

import com.github.sunnybat.paxchecker.browser.ShowclixReader;
import com.github.sunnybat.paxchecker.browser.Browser;
import java.util.Set;
import java.util.TreeSet;
// Could replace Browser with a class variable, since all this is using is getExpo(), and then be able to have multiple instances of this running

/**
 *
 * @author Sunny
 */
public class CheckShowclix extends Check {

  static final String BASE_SHOWCLIX_LINK = "http://www.showclix.com/event/";
  Set<String> alreadyChecked = new TreeSet<>();
  String currentLink;

  public CheckShowclix() {
    super();
  }

  @Override
  public synchronized void init(com.github.sunnybat.paxchecker.gui.Status s, java.util.concurrent.Phaser cB) {
    super.init(s, cB);
    updateLabel(s, "Initializing Showclix...");
  }

  @Override
  public synchronized boolean ticketsFound() {
    return !alreadyChecked.contains(currentLink);
  }

  @Override
  public synchronized void updateLink() {
//    if (!deepCheckShowclix) {
    Set<String> mySet = ShowclixReader.getAllEventURLs(Browser.getExpo());
    for (String i : mySet) {
      if (!mySet.contains(i)) {
        System.out.println("Not checked: " + i);
        if (ShowclixReader.isPaxPage(i)) {
          System.out.println("Is PAX Page!");
          currentLink = i;
          return;
        }
      }
    }
  }

  @Override
  public synchronized String getLink() {
    return currentLink;
  }

  @Override
  public synchronized void updateGUI(com.github.sunnybat.paxchecker.gui.Status s) {
    updateLabel(s, "Current Showclix Link: " + getLink());
  }

  @Override
  public synchronized void reset() {
    Set<String> mySet = ShowclixReader.getAllEventURLs(Browser.getExpo());
    for (String i : mySet) {
      alreadyChecked.add(i);
      currentLink = i;
    }
  }

}
