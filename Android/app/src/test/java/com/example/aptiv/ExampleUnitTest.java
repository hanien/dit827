package com.example.aptiv;

import com.example.aptiv.Model.Classe.Profile;
import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.Model.Helper.ProfileHandler;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private ProfileHandler _p = new ProfileHandler(null,null,null,null,null,null);

    @Test
    public void ZonesValueHandler_nullP_nullZ(){
        Profile p = new Profile(null,null,null,null,null,null,null,null,null,null,null,null);
        Zone z = new Zone(null,null,null,null,null,null,null,null,null,null,null,null);
        assertTrue(_p.ZonesValueHandler(p,z));
    }

    @Test
    public void ZonesValueHandler_P_nullZ(){
        Profile p = new Profile("middle","20","18.5","0","0","500","26","35","2","1122","1600","1600");
        Zone z = new Zone(null,null,null,null,null,null,null,null,null,null,null,null);
        assertFalse(_p.ZonesValueHandler(p,z));
    }

    @Test
    public void ZonesValueHandler_nullP_Z(){
        Profile p = new Profile(null,null,null,null,null,null,null,null,null,null,null,null);
        Zone z = new Zone(Zone.ZoneName.MIDDLE,"20","18.5","0","0","500","26","35","5","1122","1600","1600");
        assertFalse(_p.ZonesValueHandler(p,z));
    }

    @Test
    public void ZonesValueHandler_vnullP_vnullZ(){
        Profile p = null;
        Zone z = null;
        assertFalse(_p.ZonesValueHandler(p,z));
    }

    @Test
    public void ZonesValueHandler_P_Z_Overlap(){
        Profile p = new Profile("middle","20","18.5","0","0","500","26","35","2","1122","1600","1600");
        Zone z =  new Zone(Zone.ZoneName.MIDDLE,"200","18.5","0","0","500","26","35","5","1122","1600","1600");
        assertFalse(_p.ZonesValueHandler(p,z));
    }

    @Test
    public void ZonesValueHandler_P_Z_NoOverlap(){
        Profile p = new Profile("middle","20","18.5","0","0","500","26","35","2","1122","1600","1600");
        Zone z =  new Zone(Zone.ZoneName.MIDDLE,"20","18.5","0","0","500","26","35","5","1122","1600","1600");
        assertTrue(_p.ZonesValueHandler(p,z));
    }
}