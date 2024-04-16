import {Card, Result, Spin} from "antd";
import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import {lazy, Suspense} from "react";
import LayoutWrapper from "./layout/LayoutWrapper/LayoutWrapper.tsx";
import HomePage from "./pages/HomePage.tsx";
import ProfilePage from "./pages/ProfilePage.tsx";


const DeliveryListPage = lazy(() => import("./pages/DeliveryListPage.tsx"));
const DeliveryDetailsPage = lazy(() => import("./pages/DeliveryDetailsPage.tsx"));
const DeliveryHistoryPage = lazy(() => import("./pages/DeliveryHistoryPage.tsx"));

function App() {

  return (
      <BrowserRouter>
        <LayoutWrapper>
          <Suspense fallback={<Spin fullscreen spinning={true}/>}>
            <Routes>
              <Route path="/" element={<HomePage/>}/>
              <Route path="/profile" element={<ProfilePage/>}/>
              <Route path="/delivery" element={<DeliveryListPage/>}/>
              <Route path="/delivery/:deliveryId" element={<DeliveryDetailsPage/>}/>
              <Route path="/delivery-history" element={<DeliveryHistoryPage/>}/>
              <Route path="/delivery-history/:deliveryId" element={<DeliveryDetailsPage/>}/>
              <Route path="*" element={
                <Card>
                  <Result
                      status="404"
                      title="404"
                      subTitle="Sorry, the page you visited does not exist."
                      extra={<Link to={"/"}>Back Home</Link>}
                  />
                </Card>
              }/>
            </Routes>
          </Suspense>
        </LayoutWrapper>
      </BrowserRouter>

  )
}

export default App
