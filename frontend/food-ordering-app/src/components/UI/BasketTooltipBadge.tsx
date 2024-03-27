import {Badge, Tooltip} from "antd";
import {useBasket} from "../../hooks/useBasketContext.tsx";
import {animated, useSpring} from "react-spring";


const BasketTooltipBadge = () => {

  const {calculateTotalPrice, totalItems} = useBasket();
  const AnimatedBadge = animated(Badge);

  const springProps = useSpring({
    to: {transform: 'scale(1.1)'},
    from: {transform: 'scale(1)'},
    reset: true,
    reverse: false,
  });

  return (
      <Tooltip
          title={`Total: €${calculateTotalPrice().toFixed(2)}`}>
        <AnimatedBadge
            offset={[7, -5]}
            count={totalItems}
            overflowCount={99}
            style={{backgroundColor: '#52c41a', ...springProps}}/>
      </Tooltip>
  );
};

export default BasketTooltipBadge;