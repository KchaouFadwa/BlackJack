package cards;

public class PlayerCardHand extends CardHand
{
	private static final long serialVersionUID = 1L;


	public PlayerCardHand()
    {
        super();
    }
    
   
    public boolean add(Card card)
    {
        boolean cardAdded = false;
        
        if (!isperd() && !hasBlackjack())
        {
            cardAdded = super.add(card);
                        
            if (isperd())
            {
                for (Card eachCard : this)
                {
                    eachCard.getFace().switchAce();
                    
                    if (!isperd())                    
                    {
                        break;
                    }
                }
            }
        }
        
        return (cardAdded) ? true : false;
    }
}