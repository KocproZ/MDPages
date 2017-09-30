ace.define("ace/snippets/django",["require","exports","module"], function(require, exports, module) {
"use strict";

exports.snippetText = "# Model Fields\n\
\n\
# Note: Optional arguments are using defaults that match what Django will use\n\
# as a default, e.g. with max_length fields.  Doing this as a form of self\n\
# documentation and to make it easy to know whether you should override the\n\
# default or not.\n\
\n\
# Note: Optional arguments that are booleans will use the opposite since you\n\
# can either not specify them, or override them, e.g. auto_now_add=False.\n\
\n\
snippet auto\n\
	${1:FIELDNAME} = model.AutoField(${2})\n\
snippet bool\n\
	${1:FIELDNAME} = model.BooleanField(${2:default=True})\n\
snippet char\n\
	${1:FIELDNAME} = model.CharField(max_length=${2}${3:, blank=True})\n\
snippet comma\n\
	${1:FIELDNAME} = model.CommaSeparatedIntegerField(max_length=${2}${3:, blank=True})\n\
snippet date\n\
	${1:FIELDNAME} = model.DateField(${2:auto_now_add=True, auto_now=True}${3:, blank=True, null=True})\n\
snippet datetime\n\
	${1:FIELDNAME} = model.DateTimeField(${2:auto_now_add=True, auto_now=True}${3:, blank=True, null=True})\n\
snippet decimal\n\
	${1:FIELDNAME} = model.DecimalField(max_digits=${2}, decimal_places=${3})\n\
snippet email\n\
	${1:FIELDNAME} = model.EmailField(max_length=${2:75}${3:, blank=True})\n\
snippet file\n\
	${1:FIELDNAME} = model.FileField(upload_to=${2:path/for/upload}${3:, max_length=100})\n\
snippet filepath\n\
	${1:FIELDNAME} = model.FilePathField(path=${2:\"/abs/path/to/dir\"}${3:, max_length=100}${4:, match=\"*.ext\"}${5:, recursive=True}${6:, blank=True, })\n\
snippet float\n\
	${1:FIELDNAME} = model.FloatField(${2})\n\
snippet image\n\
	${1:FIELDNAME} = model.ImageField(upload_to=${2:path/for/upload}${3:, height_field=height, width_field=width}${4:, max_length=100})\n\
snippet int\n\
	${1:FIELDNAME} = model.IntegerField(${2})\n\
snippet ip\n\
	${1:FIELDNAME} = model.IPAddressField(${2})\n\
snippet nullbool\n\
	${1:FIELDNAME} = model.NullBooleanField(${2})\n\
snippet posint\n\
	${1:FIELDNAME} = model.PositiveIntegerField(${2})\n\
snippet possmallint\n\
	${1:FIELDNAME} = model.PositiveSmallIntegerField(${2})\n\
snippet slug\n\
	${1:FIELDNAME} = model.SlugField(max_length=${2:50}${3:, blank=True})\n\
snippet smallint\n\
	${1:FIELDNAME} = model.SmallIntegerField(${2})\n\
snippet text\n\
	${1:FIELDNAME} = model.TextField(${2:blank=True})\n\
snippet time\n\
	${1:FIELDNAME} = model.TimeField(${2:auto_now_add=True, auto_now=True}${3:, blank=True, null=True})\n\
snippet url\n\
	${1:FIELDNAME} = model.URLField(${2:verify_exists=False}${3:, max_length=200}${4:, blank=True})\n\
snippet xml\n\
	${1:FIELDNAME} = model.XMLField(schema_path=${2:None}${3:, blank=True})\n\
# Relational Fields\n\
snippet fk\n\
	${1:FIELDNAME} = model.ForeignKey(${2:OtherModel}${3:, related_name=''}${4:, limit_choices_to=}${5:, to_field=''})\n\
snippet m2m\n\
	${1:FIELDNAME} = model.ManyToManyField(${2:OtherModel}${3:, related_name=''}${4:, limit_choices_to=}${5:, symmetrical=False}${6:, through=''}${7:, db_table=''})\n\
snippet o2o\n\
	${1:FIELDNAME} = model.OneToOneField(${2:OtherModel}${3:, parent_link=True}${4:, related_name=''}${5:, limit_choices_to=}${6:, to_field=''})\n\
\n\
# Code Skeletons\n\
\n\
snippet form\n\
	class ${1:FormName}(forms.Form):\n\
		\"\"\"${2:docstring}\"\"\"\n\
		${3}\n\
\n\
snippet model\n\
	class ${1:ModelName}(model.Model):\n\
		\"\"\"${2:docstring}\"\"\"\n\
		${3}\n\
		\n\
		class Meta:\n\
			${4}\n\
		\n\
		def __unicode__(self):\n\
			${5}\n\
		\n\
		def save(self, force_insert=False, force_update=False):\n\
			${6}\n\
		\n\
		@model.permalink\n\
		def get_absolute_url(self):\n\
			return ('${7:view_or_url_name}' ${8})\n\
\n\
snippet modeladmin\n\
	class ${1:ModelName}Admin(admin.ModelAdmin):\n\
		${2}\n\
	\n\
	admin.site.register($1, $1Admin)\n\
	\n\
snippet tabularinline\n\
	class ${1:ModelName}Inline(admin.TabularInline):\n\
		model = $1\n\
\n\
snippet stackedinline\n\
	class ${1:ModelName}Inline(admin.StackedInline):\n\
		model = $1\n\
\n\
snippet r2r\n\
	return render_to_response('${1:template.html}', {\n\
			${2}\n\
		}${3:, context_instance=RequestContext(request)}\n\
	)\n\
";
exports.scope = "django";

});
