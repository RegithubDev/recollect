UPDATE backend_servicecategory SET category_url = '/recollect/v1/mobile/list-bio-waste-categories' WHERE id = 1;
UPDATE backend_servicecategory SET category_url = '/recollect/v1/mobile/list-scrap-categories' WHERE id = 2;

UPDATE backend_servicecategory SET order_url = '/recollect/v1/order/place-bio-waste-order' WHERE id = 1;
UPDATE backend_servicecategory SET order_url = '/recollect/v1/order/place-scrap-order' WHERE id = 2;
