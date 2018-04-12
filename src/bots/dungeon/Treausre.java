package bots.dungeon;

import bots.Dungeon;

public class Treausre extends DungeonRoom
{
    public Treausre (Dungeon dungeon)
    {
        super(RoomTypes.TreasureRoom, dungeon);
        this.terminus = true;
        dungeon.setEndMessage("You can now celebrate a successful dungeon raid" +
                " with your team.");
    }
}
