# PRINFO 5 

**Project : *COMICS LIBRARY***

Started : *24/10/2022*

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
`git checkout develop`  
Create feature branch :
`git checkout -b feature_branch`

### Naming feature branch
When creating a new feature branch, take care of the name, it has to be coherent with the task declared on Jira ! 


## Finish a feature branch
When you have finished the development work on the feature, the next step is to merge the feature branch into the develop branch :  

Positioning on the development branch : 
`git checkout develop`  
Merge your feature branch : 
`git merge feature_branch`


## Deploying new main version 
Positioning on the main branch : 
`git checkout main`  
Merge your develop branch : 
`git merge develop`










