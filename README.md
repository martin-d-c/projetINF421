# Project INF 421 : MATCHING MECHANISMS FOR KIDNEY TRANSPLANTATIONS
*Martin de CLOSETS - Erwan UMLIL*

Repository for the [Kidney Exchange project](https://marceaucoupechoux.wp.imt.fr/files/2020/11/projectINF421-sujet.pdf ) of the course INF 421 : Design and Analysis of algorithms

## Project Structure
```
.
├── README.md
├── KidneyExchange
	├── lib : SSC library
	├── report : project's report (in french)  
	├── src : implemented classes
		├── AssignationGraph.java
		├── CompatibilityGraph.java
		├── CyclesAndChainsMatching.java
		├── DirectDonationMatching.java
		├── DirectCompatibilityGraph.java
		├── Graph.java
		├── GreedyMatching.java
		├── ILPMatching.java
		├── Matching.java
		├── Patient.java
		├── QuantitativeAnalysis.java
		├── Test.java
		├── TestILP.java
	├── main_results.txt
	├── results_ILP.txt
	├── results.txt
	├── test1.txt
	├── test2.txt
	├── testPrimaire.txt
	├── ...
```
## Usage

To perform a matching, it is first necessary to construct an object of a class derived from Matching (the one corresponding to the desired matching method). Then, the match() method performs the matching. 

The construction is done thanks to a text file whose format is (except for ILPMatching) :
	- The first line contains the number of patients n
	- The next n lines contain the preferences of each patient 
	- The last line contains the blood groups of each patient (useful only for the QuantitativeAnalysis part)
For CyclesAndChainsMatching, it is necessary to specify later the selection rule to use with CyclesAndChainsMatching.ruleB = true or false.

The Test class performs tests of the DirectDonation, GreedyMaching and CyclesAndChainsMatching methods from the configuration of the question 4 represented in the testPrimaire.txt file and from
the search for minimal infeasible paths for test1.txt

The TestILP class tests ILPMatching with the test1.txt and test2.txt files and displays the results in results_ILP.txt. We use the LP solver from the [SSC](https://www.ssclab.org/en/index.html) library.

The QuantitativeAnalysis class generates 100 configurations, performs the tests and displays the results in quantitative_analysis.txt.
