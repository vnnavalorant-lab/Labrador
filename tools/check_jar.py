#!/usr/bin/env python3
"""Checks that a built jar is a valid Forge mod.  Usage: python check_jar.py path/to/mod.jar"""
import sys, zipfile

if len(sys.argv) != 2:
    sys.exit("usage: python check_jar.py <jar>")
z = zipfile.ZipFile(sys.argv[1])
names = set(z.namelist())
ok = True

def check(label, cond, hint=""):
    global ok
    print(("[OK]   " if cond else "[FAIL] ") + label + ("" if cond else "  -> " + hint))
    ok &= cond

check("META-INF/mods.toml", "META-INF/mods.toml" in names, "this is not a Forge mod jar (wrong file / zip of the source?)")
if "META-INF/mods.toml" in names:
    toml = z.read("META-INF/mods.toml").decode("utf-8", "replace")
    check("version token replaced", "@version@" not in toml, "processResources did not run; build with gradle")
check("pack.mcmeta", "pack.mcmeta" in names)
classes = [n for n in names if n.endswith(".class")]
check("compiled classes (%d)" % len(classes), any(n.startswith("com/example/labradorpuppy/") for n in classes),
      "no .class files: you are using the source zip or a -sources jar")
check("mesh + texture", "assets/labrador_puppy/mesh/puppy_mesh.bin" in names
      and "assets/labrador_puppy/textures/entity/labrador_puppy.png" in names)
check("biome modifier", "data/labrador_puppy/forge/biome_modifier/puppy_spawns.json" in names)
check("not a -sources jar", not any(n.endswith(".java") for n in names), "use the jar WITHOUT -sources in the name")
print("\nJAR LOOKS GOOD" if ok else "\nJAR HAS PROBLEMS")
sys.exit(0 if ok else 1)
