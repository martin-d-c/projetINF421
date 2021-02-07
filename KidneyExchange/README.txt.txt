MATCHING MECHANISMS FOR KIDNEY TRANSPLANTATIONS
Martin de CLOSETS - Erwan UMLIL

Pour réaliser un appariement il convient tout d'abord de construire un objet d'une classe dérivée de Matching (celle correspondant à la méthode d'appariement voulue). 
Puis, la méthode match() réalise l'appariement. 

La construction se fait grâce à un fichier texte dont le format est (excepté pour ILPMatching) :
	- La première ligne contient le nombre de patients n
	- Les n suivantes contiennent les préférences de chaque patient 
	- La dernière contient les groupes sanguins de chaque patient (utile uniquement pour la partie QuantitativeAnalysis)
Pour CyclesAndChainsMatching, il convient de préciser ultérieurement la règle de sélection à utiliser avec : CyclesAndChainsMatching.ruleB = true or false.

La classe Test réalise des tests des méthodes DirectDonation, GreedyMaching et CyclesAndChainsMatching à partir de la configuration de la question 4 représentée dans le fichier testPrimaire.txt et de
la recherche des minimal infeasible paths pour test1.txt

La classe TestILP teste ILPMatching avec les fichiers test1.txt et test2.txt et affiche les résultats dans results_ILP.txt. Nous utilisons un solver LP dont la description est fournie au lien suivant : https://www.ssclab.org/en/index.html

La classe QuantitativeAnalysis génère 100 configurations, effectue les tests et affiche les résultats dans results.txt.