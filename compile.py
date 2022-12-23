import os

out = ["Boitille.java"]
tree = {
    "field": ["Field"],
    "frames": ["Game"],
    "marble": ["Marble"],
    "utils": ["TchoucTchouc"],
    "window": ["Window"]
}

java = "javac "
jar = f"jar cmf Main.mf Main.jar Boitille.java Boitille.class "

for base in out:
    java += base + " "

for el in tree:
    for le in tree[el]:
        x = el + "/" + le
        java += x + ".java "
        jar += f"{x}.java {x}.class "

print(java)
os.system(java)

with open("Main.mf", "w") as file:
    file.write("Manifest-Version: 1.0\nMain-Class: Boitille\n")

print(jar)
os.system(jar)

print("Jar is ready to use, please use: java -jar Boitille.jar to start")
