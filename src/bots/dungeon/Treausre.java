package bots.dungeon;

import bots.Dungeon;

public class Treausre extends DungeonRoom
{
    public Treausre (Dungeon dungeon)
    {
        super(RoomTypes.TreasureRoom, dungeon);
        this.terminus = true;
    }
}
