# alphaSort

This data structure allows for a fast way of determining if a word is in a large list at the expense of setup time.

Test output:

```
Dictionary size: 370099
Test list size: 8605
Building tree: 1120 ms
Testing ArrayList.contains: 15442 ms
Testing alphaTree.isWord: 19 ms
Testing concurrence: 
Inconsistencies: 0
Total execution time: 17269 ms
```

With a lists of the above size, the isWord method is 3 orders of magnitude faster than ArrayList.contains.
