package ru.mai.diplom.tester.db.model;

/**
 * Типы документов и виртуальных сущностей
 */
public enum DocumentType {
    // TODO: заменить на свои типы документов
    APPTEMPLATE_DOCUMENT,
    APPTEMPLATE_REGISTER,
    VIRTUAL_GIS_LINKS
}

/*
Виртуальная сущность VIRTUAL_GIS_LINKS - связи документа APPTEMPLATE_DOCUMENT с GIS

Идентификатор VIRTUAL_GIS_LINKS совпадает с идентификатором документа APPTEMPLATE_DOCUMENT

Модель данных VIRTUAL_GIS_LINKS:
{
	"docId": "идентификатор документа",
	"links": [
		{
			"gisId": "идентификатор контура GIS",
			"status": "статус связи"
		},
		...
		{
			"gisId": "идентификатор контура GIS",
			"status": "статус связи"
		}
	]
}

Операции (внимание: для логирования изменения и удаления предваряйте ее операцией test, отображающей изменяемое и удаляемое):

Добавление связи:
{
	"op": "add",
	"path": "/links/-",
	"value": {
		"gisId": "cbf6ad7b-b5bb-44d6-93a1-ea8a24112885",
		"status": "review"
	}
}

Изменение связи:
[
	{
		"op": "test",
		"path": "/links/-",
		"value": {
			"gisId": "cbf6ad7b-b5bb-44d6-93a1-ea8a24112885",
			"status": "review"
		}
	}, {
		"op": "replace",
		"path": "/links/-/status",
		"value": "approve"
	}
]

Удаление связи:
[
	{
		"op": "test",
		"path": "/links/-",
		"value": {
			"gisId": "cbf6ad7b-b5bb-44d6-93a1-ea8a24112885",
			"status": "review"
		}
	}, {
		"op": "remove",
		"path": "/links/-"
	}
]
*/
