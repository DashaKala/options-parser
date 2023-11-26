Option Parser
==========

Knihovna pro zpracování a čtení voleb (vč. parametrů) z příkazového řádku, pro použití
v konzolových aplikacích.

## Deklarativní definice voleb

Každou volbu s příslušnými parametry je potřeba zadat do textového souboru.  
VZOR: *doc/options-config-template.csv*

Pozn. 
- řádky začínající `#` představují komentář
- separátory:
1. `|` odděluje seznam nebo výčtový typ
2. `;`separuje jednotlivé konfigurace každé volby 
- příklad použití metod v aplikaci využívající tuto knihovnu jsou v *OptionsParserTest.java*


