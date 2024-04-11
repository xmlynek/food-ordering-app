import React from "react";
import {
  Drawer, Spin,
  Typography
} from "antd";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {KitchenTicketDetailsRestDTO} from "../../model/kitchenTicket.ts";
import {
  completeKitchenTicket,
  fetchKitchenTicketDetailsById
} from "../../client/kitchenTicketsApiClient.ts";
import KitchenTicketDetails from "./KitchenTicketDetails.tsx";


const {Title} = Typography;

interface KitchenTicketDetailsWrapperProps {
  ticketId: string;
  restaurantId: string;
  isVisible: boolean;
  onClose: () => void;
}

const KitchenTicketDetailsWrapper: React.FC<KitchenTicketDetailsWrapperProps> = ({ticketId, isVisible, restaurantId, onClose}) => {
  const queryClient = useQueryClient();


  const {
    data: ticketDetails,
    refetch,
    isPending,
    error
  } = useQuery<KitchenTicketDetailsRestDTO, Error>({
    queryKey: ["kitchen-ticket-details", restaurantId, ticketId],
    queryFn: fetchKitchenTicketDetailsById.bind(null, restaurantId, ticketId),
    enabled: isVisible
  });

  const {mutateAsync} = useMutation({
    mutationKey: ["complete-kitchen-ticket", restaurantId, ticketId],
    mutationFn: completeKitchenTicket.bind(null, restaurantId, ticketId),
    onSuccess: async () => {
      await queryClient.invalidateQueries({queryKey: ['kitchen-tickets', restaurantId]});
    }
  });

  const handleOnClose = () => {
    // navigate(`..`);
    onClose();
  }

  const handleCompleteTicket = async () => {
    await mutateAsync();
    await refetch();
  }


  return (
      <Drawer
          title={<Title level={4} style={{margin: 0}}>Kitchen Ticket Details</Title>}
          width={720}
          onClose={handleOnClose}
          open={isVisible}
      >

        {isPending && <Spin size={"large"} fullscreen={true} spinning={true} tip="Loading..."/>}
        {error && <p>Error: {error.message}</p>}
        {!isPending && !error && ticketDetails &&
            <KitchenTicketDetails ticketDetails={ticketDetails}
                                  onCompleteTicket={handleCompleteTicket} isPending={isPending}/>}

      </Drawer>
  );
};

export default KitchenTicketDetailsWrapper;