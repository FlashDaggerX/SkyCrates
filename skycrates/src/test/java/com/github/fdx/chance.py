from random import random
from math import floor

rarity = {
    "LESS":         1.5, 
    "NORMAL":       1,
    "GREAT":        0.5, 
    "GREATEST":     0.25 
}

def decide(seed=random()):
    print("Chance Seed: {}".format(seed))
    chance = {}
    for k in rarity:
        chance[k] = (seed * rarity[k])
        print("{}: {}".format(k, chance[k]))
    
    # The limiter algorithm. Decides whether the item is placed or not.
    seed_s = random()
    print("\nLimiter Seed: {}".format(seed_s))
    limiter = (seed*2) * (chance["GREATEST"] * chance["GREAT"]) * 100
    print("Limiter: {}".format(limiter))

    for k in chance:
        print("{}: {}".format(k, chance[k] > limiter))

    print("---------\n")

decide()
