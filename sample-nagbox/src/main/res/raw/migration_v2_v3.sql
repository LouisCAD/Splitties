ALTER TABLE tasks
ADD COLUMN "display_order" INTEGER;

UPDATE tasks
SET "display_order" = "_id"