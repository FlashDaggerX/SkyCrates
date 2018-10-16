from random import random
from math import floor

rarity = {
    "LESS":         1.5, 
    "NORMAL":       1,
    "GREAT":        0.5, 
    "GREATEST":     0.25 
}

def decide(seed=random()):
    print("Chance Seed: {}\n".format(seed))
    for k in rarity:
        chance = floor((seed * rarity[k]) * 100)
        print("{}: {}".format(k, chance))
    
    limiter = floor((seed * 100)**(1/2)*25)
    print("\nLimiter: {}".format(limiter))

decide()
