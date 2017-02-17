CREATE TABLE tasks (
    "_id" INTEGER PRIMARY KEY,
    "title" TEXT NOT NULL,
    "interval" INTEGER NOT NULL,
    "flags" INTEGER NOT NULL,
    "next_fire_at" INTEGER,
    "last_started_at" INTEGER,
    "display_order" INTEGER)
