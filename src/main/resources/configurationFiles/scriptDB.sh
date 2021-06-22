#!/bin/bash

#Script to create a SQL for cardnames. Every row in input file have to be in pattern (idCard;nameCard;typeCard)

rm output.txt
touch output.txt
chmod 777 output.txt
sed -i -e 's/ /_/g' -e  's/"""/\"/g' -e 's/""/\"/g' -e 's/;"/;/g' input.csv
echo "insert into cardnames (id, name, card_type) values" >> output.txt
for WERSTEXT in $(cat input.csv); do
	idCard=$(echo $WERSTEXT | cut -d ';' -f 1)
	nameCard=$(echo $WERSTEXT | cut -d ';' -f 2)
	typeCard=$(echo $WERSTEXT | cut -d ';' -f 3)
	echo "($idCard, \"$nameCard\", $typeCard)," >> output.txt
	echo "Added $nameCard"
done
sed -i 's/_/ /g' output.txt

