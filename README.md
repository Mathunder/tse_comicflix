# PRINFO 5 

**Project : *COMICS LIBRARY***

Started : *24/10/2022*  
Deadline : *24/01/2023*

# DEVS 

- Maël CHOSSAT
- Rochdi Failali
- Thomas PEZDA
- Matthieu D'Hoop
- Romain Boulouis
- Axel ANTOINE

# Contribute guidelines

## Workflow

**2 important branches :**

- **main :** Only tested functionnal versions of the project (deliverables) with versionning tag (V1.0,V1.1,...)
- **develop:** Integration branch for features
    - feature1 : respective to the functionality/features under development
    - feature..
    - featureN  
**Feature branches use "develop" as a parent branch ! NOT MAIN !**

**When a feature is finished, it is merged back into the development branch.**, 

## Create a new feature branch
Positioning on the development branch :   
On Eclipse, in the *Git Repositories panel*, double click on the branch develop in *Branches/**local***  
Create feature branch :  
Right click on *Local* in *Branches* and after follow *Switch to* -> *New branch*

### Naming feature branch
When creating a new feature branch, take care of the name, **it has to be coherent with the task declared on Jira !**  
To get a name, go on Jira, click on the ticket associated to the feature and look at the panel *"Details-->Developpement"* : just next to *"Créer une branche"*, click on the right arrow and next click on the copy button to get the command line. Be carefull the name can be very long according to the title of the ticket !  

## Finish a feature branch
When you have finished the development work on the feature, the next step is to merge the feature branch into the develop branch, but to limit the risk of conflict you have to create a merge request on Gitlab that have to be accepted by Axel before merging in the develop branch.

