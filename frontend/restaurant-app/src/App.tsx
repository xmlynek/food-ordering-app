import {Result, Spin} from "antd";
import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import {lazy, Suspense} from "react";
import ModifyMenuPage from "./pages/ModifyMenuPage.tsx";
import CreateMenuPage from "./pages/CreateMenuPage.tsx";
import CreateRestaurantPage from "./pages/CreateRestaurantPage.tsx";
import KitchenTicketDetailsPage from "./pages/KitchenTicketDetailsPage.tsx";
import LayoutWrapper from "./layout/LayoutWrapper/LayoutWrapper.tsx";

const HomePage = lazy(() => import("./pages/HomePage.tsx"));
const RestaurantsListPage = lazy(() => import("./pages/RestaurantsListPage.tsx"));
const RestaurantPage = lazy(() => import("./pages/RestaurantPage.tsx"));
const OrderTicketsPage = lazy(() => import("./pages/KitchenTicketsPage.tsx"));
const MenusPage = lazy(() => import("./pages/MenusPage.tsx"));
const ProfilePage = lazy(() => import("./pages/ProfilePage.tsx"));


function App() {

  return (
      <BrowserRouter>
        <LayoutWrapper>
            <Suspense fallback={<Spin fullscreen spinning={true}/>}>
              <Routes>
                <Route path="/" element={<HomePage/>}/>
                <Route path="/restaurants" element={<RestaurantsListPage/>}>
                  <Route path="add" element={<CreateRestaurantPage/>}/>
                </Route>
                <Route path="/restaurants/:id" element={<RestaurantPage/>}>
                  <Route path="tickets" element={<OrderTicketsPage/>}>
                    <Route path=":ticketId" element={<KitchenTicketDetailsPage/>}/>
                  </Route>
                  <Route path="menu" element={<MenusPage/>}>
                    <Route path="add" element={<CreateMenuPage/>}/>
                    <Route path=":menuId/edit" element={<ModifyMenuPage/>}/>
                  </Route>
                </Route>
                <Route path="/profile" element={<ProfilePage/>}/>
                <Route path="*" element={<Result
                    status="404"
                    title="404"
                    subTitle="Sorry, the page you visited does not exist."
                    extra={<Link to={"/"}>Back Home</Link>}
                />}/>
              </Routes>
            </Suspense>
        </LayoutWrapper>
      </BrowserRouter>

  )
}

export default App
