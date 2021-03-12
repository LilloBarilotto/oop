/*--------------*-----------------------------------------------------------*\
*|   ######     | CLASS SAMPLE FOR "OBJECT ORIENTED PROGRAMMING" (04JEY)     *
*|  #######     | (c) Mar 2021, Giovanni Squillero <squillero@polito.it>     *
*|  ####   \    | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ *
*|   ##G   c\   | Copying and distributing this file for classroom use,      *
*|   ##     _\  | either with or without modification, are permitted without *
*|   |    _/    | royalties provided that this 9-line comment is preserved.  *
*|   |   _/     | ===> THIS FILE IS OFFERED AS-IS, WITHOUT ANY WARRANTY <=== *
\*--------------*-----------------------------------------------------------*/

public class TestApp {

	public static void main(String[] args) {
		Spell s1 = new Spell();

		s1.name = "Affuchiti co spagu ra sasizza";

		s1.cast();
		
		Spell ref;
		ref = s1;
		ref.name = "Magic hand";
		s1.cast();
	}
}
