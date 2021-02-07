MATCHING MECHANISMS FOR KIDNEY TRANSPLANTATIONS
Martin de CLOSETS - Erwan UMLIL

Pour r�aliser un appariement il convient tout d'abord de construire un objet d'une classe d�riv�e de Matching (celle correspondant � la m�thode d'appariement voulue). 
Puis, la m�thode match() r�alise l'appariement. 

La construction se fait gr�ce � un fichier texte dont le format est (except� pour ILPMatching) :
	- La premi�re ligne contient le nombre de patients n
	- Les n suivantes contiennent les pr�f�rences de chaque patient 
	- La derni�re contient les groupes sanguins de chaque patient (utile uniquement pour la partie QuantitativeAnalysis)
Pour CyclesAndChainsMatching, il convient de pr�ciser ult�rieurement la r�gle de s�lection � utiliser avec : CyclesAndChainsMatching.ruleB = true or false.

La classe Test r�alise des tests des m�thodes DirectDonation, GreedyMaching et CyclesAndChainsMatching � partir de la configuration de la question 4 repr�sent�e dans le fichier testPrimaire.txt et de
la recherche des minimal infeasible paths pour test1.txt

La classe TestILP teste ILPMatching avec les fichiers test1.txt et test2.txt et affiche les r�sultats dans results_ILP.txt. Nous utilisons un solver LP dont la description est fournie au lien suivant : https://www.ssclab.org/en/index.html

La classe QuantitativeAnalysis g�n�re 100 configurations, effectue les tests et affiche les r�sultats dans results.txt.