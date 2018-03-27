package cards;


public class DealerCardHand extends CardHand
{

	private static final long serialVersionUID = 1L;


	public DealerCardHand()
    {
        super();
    }
    
   
    public boolean add(Card card)
    {
        boolean cardAdded = false;
        
        if (!isperd() && !hasBlackjack())
        {            
            cardAdded = super.add(card);
        }
        
        return (cardAdded) ? true : false;
    }
}