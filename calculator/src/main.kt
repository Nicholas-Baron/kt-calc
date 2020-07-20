fun main()
{

    var myList = shunt(mutableListOf(Integer(1), Minus(), LeftParenthesis, Integer(4),Plus(),Integer(7),
            Plus(), Integer(5), Multiply(), Integer(9), RightParenthesis)) as MutableList<Token>
    while(myList.isNotEmpty())
    {
        println(myList.dequeueOrNull())
    }
}
