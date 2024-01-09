# Split-and-Merge
Project by:
	Tom Daguerre
	Zoé Coudray

This project allows the user to read 3D niftii images, then, it will apply an algorithm to split and merge the said image.
It writes the new image in another niftii file.
We worked on the project from the end of September to the beginning of January, a big part of the project was used finding good algorithm to split and merge the images.
Our project is now finished, a french report is available in this project files. 

This project contains in the src folder: 
	In the Main folder: our different code files divided by classe;
	In the test folder: our code files for unitary tests;

Inputs:
	Homogeneity Criteria (value between 0 and 1) - defines the precision with wich the image will be splitted;
	Minimal volume - no cube will be splitted if it's volume is under this value;
	Neighbour graph creation method (0 to use our first method, anything else to use the other one) - usually, the second method will be faster;
	path to the image to read;
	path to the image to write; 

Outputs:
	New image Niftii generated after the split and merge;
	0 if no error;
	1 if not enough arguments;
 	2 if homogeneityC is out of bounds
	3 if file not found;
	4 if file's dimensions incompatible with our program;
