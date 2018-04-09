package bots.dungeon;

import bots.Dungeon;

public class TotalPartyKill extends DungeonRoom
{
    public TotalPartyKill (Dungeon dungeon)
    {
        super(RoomTypes.TotalPartyKill, dungeon);
        this.terminus = true;
    }
}
