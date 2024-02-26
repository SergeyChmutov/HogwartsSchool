ALTER TABLE student
	ALTER COLUMN age SET DEFAULT 20,
	ALTER COLUMN name SET NOT NULL,
	ADD CONSTRAINT age_min_value_constraint CHECK (age > 15),
	ADD CONSTRAINT name_unique_constraint UNIQUE (name);

ALTER TABLE faculty
	ADD CONSTRAINT name_color_unique_constraint UNIQUE (name, color);