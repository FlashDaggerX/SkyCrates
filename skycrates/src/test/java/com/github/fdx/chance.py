from random import random
from math import floor

"""
Instead of having a value always at 1, try setting
them all less than 1 so you still have a chance
to get nothing.
"""
rarity = {
    "LESS":         0.99, 
    "NORMAL":       0.77,
    "GREAT":        0.66, 
    "GREATEST":     0.33 
}

def decide(seed=random()):
    print("Chance Seed: {}".format(seed))
    chance = {}
    for k in rarity:
        chance[k] = (seed * rarity[k])
        print("{}: {}".format(k, chance[k]))

    """
    The limiter algorithm. Decides whether the item is placed or not.
    It is the seed multiplied by the length of the chance dictionary, 
    then that result is multiplied by a chosen factor. It's
    explained below.

    'chg' is a calculated percent change between the actual and seeded
    calulation, divded by the actual. The result ended up with almost
    false for all chances.

    'gbf' is the greatest value of seeded chance multiplied by the least
    valued actual chance. I honestly don't know how I pulled the numbers
    out of my hat, but the results were the expected ones. The only
    problem with it was that often, the values came out to be ALL
    true or ALL false, which isn't what I'd hoped.

    'yug' is the lesser actual chance value multiplied by the
    seeded normal chance value. That result is multiplied by the
    greatest seeded chance value. It produced the results I wanted,
    but I wanted to try one more...

    'yug_f' seemed to have done a lot better than 'yug'. Instead of
    multiplying the normal seeded chance value, I multiplied the
    normal chance value. The results are then compared with
    the greatest seeded chance value. Since this number is around
    a third of the lesser value, it will always have a chance to be false.
    """
    #chg = (rarity["GREATEST"]-chance["GREATEST"])/rarity["GREATEST"]
    #gbf = (chance["GREATEST"] * rarity["LESS"])
    yug = (rarity["LESS"]*chance["NORMAL"]) * chance["GREATEST"]
    #yug_f = (rarity["LESS"]*rarity["NORMAL"]) * chance["GREATEST"]

    limiter = (seed*4) * yug
    print("\nLimiter: {}".format(limiter))

    for k in chance:
        print("{}: {}".format(k, chance[k] > limiter))

    print("---------\n")

decide()
