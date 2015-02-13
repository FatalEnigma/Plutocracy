***TESTING READ ME***

I have set up a testing platform similar to the one we did /
were supposed to do in the advisory. It's simple enough to use,
and it will help stop everything imploding in it's own filth 
when we stick it all together. There are a number of conditions
though that must be met:

1:	***PACKAGES***
	You will need to have the following line at the very top
	of each game class you write now:
		
		package plutocracy;
	
	This is because the main game classes are now in a package
	called plutocracy (obviously), and need to be referenced
	as such. All classes in the same package can reference
	each other so no additional import statements are needed.
	
2:	***MORE PACKAGES***
	There are 3 packages in the project now:
		-plutocracy
		-plutocracyGUI
		-tests
	If you want to reference a class in a different package to
	your own, you must import it. For example, if your writing
	a game class that uses a GUI element from protocracyGUI, 
	you must do this in addition to the above:

		import protocracyGUI.*;
		
	The asterisk (*)denotes "all", so everything is imported from
	the package.

3: 	***TESTS***
	You write a test in the test package, which tests a game class,
	so (as above), you need to have the following at the very top:
	
		package tests;
		import plutocracy.*;
		
	Tests also require that they have the following name syntax:
		
		(classname)Test
		
	Essentially, just end all you testing classes' name with "Test".
	As far as writing tests go, just look at these sites:
	
		https://github.com/junit-team/junit/wiki/Getting-started
		http://junit.sourceforge.net/doc/cookbook/cookbook.htm
		
	The important thing to realise is to not program testing classes
	like regular java classes. The use of the annotations is the key.
	You can also look at my CellTest.java class to view how to do it:
	I've put some basic comments onto it.
	
4:	***GUI TESTS***
	The default version of the build.xml file also removes the GUI elements 
	from testing at build time. This is because they require developer
	interaction, which would get tiring every time you build the project.
	This means that GUI tests must be down prior to the build.

	If you want to have GUI tests performed on build, open up the
	build.xml file, and go to line 75 which should look like this:
		
		<exclude name="tests/GUI*"/>
	
	Change it to this:
		
		<!-- exclude name="tests/GUI*"/ -->

	This comments it out and GUI tests will now be performed
	on build.
	 
***ERRORS / BUGS***
1:	While vaild tests are working perfectly, which you can see by watching
	the console or reading the assosicated XML files in "reports/test-results/",
	transforming JUnit results into HTML reports using ANT via ecplise currently
	isn't working. It's quite nice though, that it's not actually my fault for
	once: it's Oracles. 
	
	There's a well documented bug that Oracle introduced in the the JDK
	which breaks this functionality.
	
	***Workaround***
	What I did was I downloaded the standalone version of Ant which does not cause the error,
	and placed it in the repositiory. Next, I created a batch file which can be used to
	execute the standalone version of ant, without hardcoding any user environment variables
	into the System properties.

	tl;dr: Double click "antBuild.bat" to build the project using ant if you want reports.
	
	***Note***
	While I will have reporting activated in my branch, it will still be deactivated in the 
	trunk. This is because using anything other than antBuild.bat to start Ant will still result
	in the error if reporting is activated. The actual building process is fine, it's just the 
	reporting that fails but Ant flags it up as "Build Failed" which might confuse people. 
	
	If you want to reactivate reporting IN YOUR OWN BRANCH, then open build.xml, go to line 33
	which looks like this:
	
		<target name="package" depends="clean, compile, test">
		
	and change "test" to "report", and save the file. Now when you use antBuild.bat, you
	get nice HTML test reports in the reports folder.
	
	
	