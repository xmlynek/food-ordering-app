import React from "react";
import {
  Drawer, Spin,
  Typography
} from "antd";
import {useNavigate, useParams} from "react-router-dom";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {KitchenTicketDetailsRestDTO} from "../model/kitchenTicket.ts";
import {
  completeKitchenTicket,
  fetchKitchenTicketDetailsById
} from "../client/kitchenTicketsApiClient.ts";
import KitchenTicketDetails from "../components/Ticket/KitchenTicketDetails.tsx";

const {Title} = Typography;

const KitchenTicketDetailsPage: React.FC = () => {
  const queryClient = useQueryClient();
  const params = useParams()
  const navigate = useNavigate();

  const restaurantId = params.id as string;
  const ticketId = params.ticketId as string;


  const {data: ticketDetails, refetch, isPending, error} = useQuery<KitchenTicketDetailsRestDTO, Error>({
    queryKey: ["kitchen-ticket-details", restaurantId, ticketId],
    queryFn: fetchKitchenTicketDetailsById.bind(null, restaurantId, ticketId)
  });

  const {mutateAsync, isPending: mutationPending, error: mutationError} = useMutation({
    mutationKey: ["complete-kitchen-ticket", restaurantId, ticketId],
    mutationFn: completeKitchenTicket.bind(null, restaurantId, ticketId),
    onSuccess: async () => {
      await queryClient.invalidateQueries({queryKey: ['kitchen-tickets', restaurantId]});
    }
  });

  const handleOnClose = () => {
    navigate(`..`);
  }

  const handleCompleteTicket = async () => {
    await mutateAsync();
    await refetch();
  }


  if (error) {
    return <p>Error: {error.message}</p>;
  }

  return (
      <Drawer
          title={<Title level={4} style={{margin: 0}}>Kitchen Ticket Details</Title>}
          width={520}
          onClose={handleOnClose}
          open={true}
      >
        <Spin size={"large"} fullscreen={true} spinning={isPending || mutationPending} tip="Loading..." />
        <KitchenTicketDetails ticketDetails={ticketDetails} onCompleteTicket={handleCompleteTicket} isPending={isPending}/>
      </Drawer>
  );
};

export default KitchenTicketDetailsPage;