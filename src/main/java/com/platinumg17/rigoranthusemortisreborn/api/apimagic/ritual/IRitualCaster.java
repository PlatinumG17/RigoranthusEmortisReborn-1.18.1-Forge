package com.platinumg17.rigoranthusemortisreborn.api.apimagic.ritual;

import com.platinumg17.rigoranthusemortisreborn.api.RigoranthusEmortisRebornAPI;

import java.util.ArrayList;
import java.util.List;

public interface IRitualCaster {

    List<String> getUnlockedRitualIDs();

    default List<AbstractRitual> getUnlockedRituals(){
        List<AbstractRitual> list = new ArrayList<>();
        for(String s : getUnlockedRitualIDs()){
            list.add(RigoranthusEmortisRebornAPI.getInstance().getRitual(s));
        }
        return list;
    }
    void unlockRitual(String ritualID);

    String getSelectedRitual();

    void setRitual(AbstractRitual ritual);

    void setRitual(String ritualID);
}