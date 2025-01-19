package structures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import config.Configuration;

public class BinaryTreeUtilityTest {

	private BinaryTreeNode<Integer> root1, root2, root3, root4, root5;
	private BinaryTreeNode<String> rootString;
	private BinaryTreeUtility utility;

	// This tree has over 1 million nodes
	private final BinaryTreeNode<Integer> largeTree = initLargeTree(20);

	private static final <T> BinaryTreeNode<T> node(BinaryTreeNode<T> left, T elem, BinaryTreeNode<T> right){
		return Configuration.createBinaryTreeNode(left, elem, right);
	}
	
	@BeforeEach
	public void setUp() {
		utility = Configuration.createBinaryTreeUtility();
		assertNotNull(utility,
				"It looks like you have not set your implementation of BinaryTreeUtility in the Configuration file.");
		root1 = initRoot1();
		root2 = initRoot2();
		root3 = initRoot3();
		root4 = initRoot4();
		root5 = initRoot5();
		rootString = initRootString();
		assertNotNull(root1, "It looks like your configuration file isn't set for BinaryTreeNode.");
		assertNotNull(root2, "It looks like your configuration file isn't set for BinaryTreeNode.");
		assertNotNull(root3, "It looks like your configuration file isn't set for BinaryTreeNode.");
		assertNotNull(root4, "It looks like your configuration file isn't set for BinaryTreeNode.");
		assertNotNull(root5, "It looks like your configuration file isn't set for BinaryTreeNode.");
		assertNotNull(rootString, "It looks like your configuration file isn't set for BinaryTreeNode.");
	}
	
	// Creates a tree with n levels
	private BinaryTreeNode<Integer> initLargeTree(int n){
		if(n < 0) return null;
		return node(initLargeTree(n - 1), n, initLargeTree(n - 1));
	}
	
	private BinaryTreeNode<Integer> initRoot1(){
		return node(null, 5, null);
	}
	
	private BinaryTreeNode<Integer> initRoot2(){
		//       5
		//        \
		//         7
		return node(null,
				5,
				node(null, 7, null)
		);
	}
	
	private BinaryTreeNode<Integer> initRoot3(){
		//                  5
		//              /       \
		//             3         19
		//            / \       /
		//           6   7     1
		//                    /
		//                   4
		return 
		node(
			node(
				node(null, 6, null),
					3,
					node(null, 7, null)
			),
			5,
			node(
				node(
					node(null, 4, null),
						1,
						null),
					19,
					null)
		);
	}
	
	private BinaryTreeNode<Integer> initRoot4(){
		//                  5
		//              /       \
		//             3         19
		//            / \       /
		//           1   2     1
		//                    /
		//                   4
		return
				node(
						node(
							node(null, 1, null),
								3,
								node(null, 2, null)
						),
						5,
						node(
							node(
								node(null, 4, null),
									1,
									null),
								19,
								null)
				);
	}

	private BinaryTreeNode<Integer> initRoot5(){
		//                  5
		//              /       \
		//             3         19
		//            / \       /  \
		//           1   4     7    25
		return
				node(
						node(
							node(null, 1, null),
								3,
								node(null, 4, null)
						),
						5,
						node(
								node(null, 7, null),
								19,
								node(null, 25, null)
						)
				);
	}

	private BinaryTreeNode<String> initRootString(){
		//               "life"
		//              /      \
		//          "is"      "like"
		//           /            \
		//         "a"           "of"
		//          \
		//         "box"
		//            \
		//          "chocolates"
		return
				node(
						node(
								node(null,
										"a",
										node(null,
												"box",
												node(null, "chocolates", null)
										)
								),
								"is",
								null),
						"life",
						node(null,
								"like",
								node(null, "of", null)
						)
				);
	}

	@Test //(timeout = 5000)
	@Timeout(5)
	public void testDepth(){
		assertEquals(0, utility.getDepth(root1));
		assertEquals(1, utility.getDepth(root2));

		//                  5
		//              /       \
		//             3         19
		//            / \       /
		//           6   7     1
		//                    /
		//                   4
		assertEquals(3, utility.getDepth(root3));

		//                  5
		//              /       \
		//             3         19
		//            / \       /
		//           1   2     1
		//                    /
		//                   4
		assertEquals(3, utility.getDepth(root4));

		//                  5
		//              /       \
		//             3         19
		//            / \       /  \
		//           1   4     7    25
		assertEquals(2, utility.getDepth(root5));

		//               "life"
		//              /      \
		//          "is"      "like"
		//           /            \
		//         "a"           "of"
		//          \
		//         "box"
		//            \
		//          "chocolates"
		assertEquals(4, utility.getDepth(rootString));

		assertEquals(20, utility.getDepth(largeTree));
	}
	
	@Test
	public void testDepthException() {
		assertThrows(NullPointerException.class,
				() -> utility.getDepth(null));
	}
	
	@Test //(timeout = 5000)
	@Timeout(5)
	public void testIsBalanced(){
		assertTrue(utility.isBalanced(root1, 0));
		assertTrue(utility.isBalanced(root1, 1));
		assertTrue(utility.isBalanced(root1, 2));
		
		assertFalse(utility.isBalanced(root2, 0));
		assertTrue(utility.isBalanced(root2, 1));
		assertTrue(utility.isBalanced(root2, 2));

		//                  5
		//              /       \
		//             3         19
		//            / \       /
		//           6   7     1
		//                    /
		//                   4
		assertFalse(utility.isBalanced(root3, 0));
		// consider the node with 19
		assertFalse(utility.isBalanced(root3, 1));
		assertTrue(utility.isBalanced(root3, 2));

		//                  5
		//              /       \
		//             3         19
		//            / \       /
		//           1   2     1
		//                    /
		//                   4
		assertFalse(utility.isBalanced(root4, 0));
		assertFalse(utility.isBalanced(root4, 1));
		assertTrue(utility.isBalanced(root4, 2));

		//                  5
		//              /       \
		//             3         19
		//            / \       /  \
		//           1   4     7    25
		assertTrue(utility.isBalanced(root5, 0));
		assertTrue(utility.isBalanced(root5, 1));
		assertTrue(utility.isBalanced(root5, 2));

		//               "life"
		//              /      \
		//          "is"      "like"
		//           /            \
		//         "a"           "of"
		//          \
		//         "box"
		//            \
		//          "chocolates"
		assertFalse(utility.isBalanced(rootString, 0));
		assertFalse(utility.isBalanced(rootString, 1));
		// consider the node with "is"
		assertFalse(utility.isBalanced(rootString, 2));

		assertTrue(utility.isBalanced(largeTree, 0));
		assertTrue(utility.isBalanced(largeTree, 1));
		assertTrue(utility.isBalanced(largeTree, 2));
	}
	
	@Test
	public void testIsBalancedNull(){
		assertThrows(NullPointerException.class,
				() -> utility.isBalanced(null, 1));
	}
	
	@Test
	public void testIsBalancedIllegal(){
		assertThrows(IllegalArgumentException.class,
				() -> utility.isBalanced(root1, -1));
	}
	
	@Test
	public void testIsBST(){
		assertTrue(utility.isBST(root1));
		assertTrue(utility.isBST(root2));

		//                  5
		//              /       \
		//             3         19
		//            / \       /
		//           6   7     1
		//                    /
		//                   4
		assertFalse(utility.isBST(root3));

		//                  5
		//              /       \
		//             3         19
		//            / \       /
		//           1   2     1
		//                    /
		//                   4
		assertFalse(utility.isBST(root4));

		//                  5
		//              /       \
		//             3         19
		//            / \       /  \
		//           1   4     7    25
		assertTrue(utility.isBST(root5));

		//               "life"
		//              /      \
		//          "is"      "like"
		//           /            \
		//         "a"           "of"
		//          \
		//         "box"
		//            \
		//          "chocolates"
		assertTrue(utility.isBST(rootString));
	}
	
	@Test
	public void testIsBSTException(){
		assertThrows(NullPointerException.class,
				() -> utility.isBST(null));
	}

	private void assertIteratorContains(Iterator<Integer> itr, Integer ... elems){
		List<Integer> found = new LinkedList<>();
		for(Integer e : elems){
			assertTrue(itr.hasNext(),
					"Expected iterator to produce " + Arrays.toString(elems) + " but produced " + found);
			Integer test = itr.next();
			found.add(test);
			assertEquals(test, e,
					"Expected iterator to produce " + Arrays.toString(elems) + " but start of iterator produced " + found);
		}

		if(itr.hasNext()){
			while(itr.hasNext())
				found.add(itr.next());
			fail("Expected iterator to produce " + Arrays.toString(elems) + " but produced " + found);
		}
	}
}
