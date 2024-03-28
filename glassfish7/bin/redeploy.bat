@echo off
rem Undeploy application
asadmin undeploy Restaurant_API_Menus-1.0-SNAPSHOT

rem Deploy updated application
asadmin deploy --path="C:\Users\Thibault\OneDrive\Bureau\workspace\Restaurant-API_Menus\target\Restaurant_API_Menus-1.0-SNAPSHOT.war" Restaurant_API_Menus-1.0-SNAPSHOT

rem Display success message
echo "Application redéployée avec succès !"