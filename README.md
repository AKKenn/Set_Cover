I implemented a backtracking algorithm with some simple pruning involved. Some methods I used to prune the partial solutions includes:

• Keeping track of how many sets we have in the current partial solution, and if we have more than the current minimum set cover, we ignore that branch of the recursive tree

• Only including potential set candidates that come after the current set we are working with.

I also optimized the input data slightly by sorting the input sets by their size such that the larger sets get included first before the smaller sets.

Due to my changes, data sets, especially those with very large subsets that are a part of the minimum set cover, will run much faster than the exhaustive search method I originally implemented.