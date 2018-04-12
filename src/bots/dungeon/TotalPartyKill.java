package bots.dungeon;

import bots.Dungeon;

public class TotalPartyKill extends DungeonRoom
{
    public TotalPartyKill (Dungeon dungeon)
    {
        super(RoomTypes.TotalPartyKill, dungeon);
        this.terminus = true;
        dungeon.setEndMessage("Everyone died, but oh well." +
                " The boss will just feast on your souls.");
    }
}
