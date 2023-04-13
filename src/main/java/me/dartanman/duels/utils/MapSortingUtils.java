package me.dartanman.duels.utils;

import java.util.*;

public class MapSortingUtils
{

    public static LinkedHashMap<UUID, Integer> sort(final HashMap<UUID, Integer> map)
    {
        LinkedHashMap<UUID, Integer> sorted = new LinkedHashMap<>();

        List<UUID> uuidList = new ArrayList<>(map.keySet());
        uuidList.sort(Comparator.comparing(map::get));
        Collections.reverse(uuidList);

        for (UUID uuid : uuidList)
        {
            sorted.put(uuid, map.get(uuid));
        }

        return sorted;
    }




}
